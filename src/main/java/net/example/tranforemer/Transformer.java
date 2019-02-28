package net.example.tranforemer;

import javax.servlet.http.HttpServletRequest;

public interface Transformer<V> {
    V transform(HttpServletRequest request);
}
