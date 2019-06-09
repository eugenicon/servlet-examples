package net.example.servlet;

import net.example.util.ServletUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebFilter(urlPatterns = "*", initParams = {
        @WebInitParam(name = "dispatcherUrl", value = "/view"),
        @WebInitParam(name = "permittedUrls", value = "/WEB-INF/.*,/static/.*")
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
        if (ServletUtils.requestUrlMatches(httpRequest, permittedUrls)) {
            chain.doFilter(request, response);
        } else {
            if (ServletFileUpload.isMultipartContent(httpRequest)) {
                request = new MultipartRequestWrapper(httpRequest);
            }
            String path = ServletUtils.getRequestUrl(httpRequest);
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

    public class MultipartRequestWrapper extends HttpServletRequestWrapper {
        private Map<String, List<FileItem>> parameters = Collections.emptyMap();

        public MultipartRequestWrapper(HttpServletRequest request) {
            super(request);
            ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                parameters = fileUpload.parseParameterMap(request);
            } catch (FileUploadException e) {
                // do nothing
            }
        }

        @Override
        public String getParameter(String name) {
            List<FileItem> fileItems = parameters.get(name);
            if (fileItems != null && !fileItems.isEmpty()) {
                return fileItems.get(0).getString();
            }
            return super.getParameter(name);
        }

        @Override
        public Object getAttribute(String name) {
            if (parameters.containsKey(name)) {
                List<FileItem> fileItems = parameters.get(name);
                return fileItems.toArray(new FileItem[fileItems.size()]);
            }
            return super.getAttribute(name);
        }
    }
}
