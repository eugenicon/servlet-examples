package net.example.tranforemer;

import javax.servlet.http.HttpServletRequest;

public interface Transformer<V> {
    V transform(HttpServletRequest request, String parameter) throws Exception;
    Class<V> getSupportedType();
}
