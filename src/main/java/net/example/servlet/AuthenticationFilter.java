package net.example.servlet;

import net.example.resolver.DependencyResolver;
import net.example.service.AuthenticationService;
import net.example.util.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("*")
public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    private AuthenticationService authenticationService;

    @Override
    public void init(FilterConfig filterConfig) {
        authenticationService = DependencyResolver.getComponent(AuthenticationService.class);
        authenticationService.init(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        final HttpSession session = httpRequest.getSession();

        if (ServletUtils.getRequestUrl(httpRequest).equals("/login")) {
            String userId = authenticationService.getUserId(httpRequest);
            LOGGER.debug("verify auth on {} for {}", httpRequest.getRequestURI(), userId.isEmpty() ? "[UNKNOWN USER]" : userId);
            if (authenticationService.isUserHasOtherOpenSessions(userId, session)) {
                authenticationService.finishUserSessions(userId, session);
            }
            if (!userId.isEmpty() && !authenticationService.isUserSessionStarted(userId, session)) {
                authenticationService.startUserSession(httpRequest);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
