package net.example.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = "*", initParams = {
        @WebInitParam(name = "dispatcherUrl", value = "/view"),
        @WebInitParam(name = "permittedUrls", value = "/WEB-INF/,/static/")
})
public class DispatcherFilter implements Filter {
    private String dispatcherUrl;
    private List<String> permittedUrls;

    @Override
    public void init(FilterConfig filterConfig) {
        dispatcherUrl = filterConfig.getInitParameter("dispatcherUrl");
        permittedUrls = Arrays.asList(filterConfig.getInitParameter("permittedUrls").split(","));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI().substring( httpRequest.getContextPath().length());
        if (permittedUrls.stream().anyMatch(path::startsWith)) {
            chain.doFilter(request, response);
        } else {
            String refererUrl = getRefererUrl(httpRequest);
            request.setAttribute("refererUrl", refererUrl);
            request.getRequestDispatcher(dispatcherUrl + path).forward(request, response);
        }
    }

    private String getRefererUrl(HttpServletRequest request) {
        String refererUrl = request.getHeader("Referer");
        if (refererUrl != null && refererUrl.contains(request.getContextPath())) {
            refererUrl = refererUrl.substring(refererUrl.indexOf(request.getContextPath()) + request.getContextPath().length() + 1);
        }
        return refererUrl;
    }
}
