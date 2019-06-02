package net.example.util;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ServletUtils {
    public static boolean requestUrlMatches(HttpServletRequest httpRequest, List<String> allowedUrls) {
        String requestUrl = getRequestUrl(httpRequest);
        return allowedUrls.stream().anyMatch(url -> requestUrl.matches(url));
    }

    public static String getRequestUrl(HttpServletRequest httpRequest) {
        return httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    }
}
