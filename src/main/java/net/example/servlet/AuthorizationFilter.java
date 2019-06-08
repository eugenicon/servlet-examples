package net.example.servlet;

import net.example.data.model.Role;
import net.example.resolver.DependencyResolver;
import net.example.service.AuthenticatedUser;
import net.example.service.AuthenticationService;
import net.example.util.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

@WebFilter("*")
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
    private final Map<Role, Function<HttpServletRequest, String>> rolePageRestrictions = new HashMap<>();
    private AuthenticationService authenticationService;
    private List<String> allowedForAll;

    @Override
    public void init(FilterConfig filterConfig) {
        authenticationService = DependencyResolver.getComponent(AuthenticationService.class);
        Properties properties = DependencyResolver.getComponent(Properties.class);
        allowedForAll = Arrays.asList(properties.getProperty("url.allowed.all").split(","));

        for (Role role : Role.values()) {
            String value = properties.getProperty("url.allowed." + role.name().toLowerCase());
            String target = role == Role.UNKNOWN ? "/login" : "/status/forbidden";
            List<String> allowedUrls = value != null ? Arrays.asList(value.split(",")) : Collections.singletonList(target);
            rolePageRestrictions.put(role, req -> ServletUtils.requestUrlMatches(req, allowedUrls) ? "" : target);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        if (ServletUtils.requestUrlMatches(httpRequest, allowedForAll)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Role role = authenticationService.getAuthentication(httpRequest.getSession()).map(AuthenticatedUser::getRole).orElse(Role.UNKNOWN);
            String redirectUrl = rolePageRestrictions.get(role).apply(httpRequest);

            if (redirectUrl.isEmpty()) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                LOGGER.debug("user with role {} redirected to {}", role, redirectUrl);
                ((HttpServletResponse) servletResponse).sendRedirect(httpRequest.getContextPath() + redirectUrl);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
