package net.example.tranforemer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;

public interface Transformer<V> {
    V transform(HttpServletRequest request, Parameter parameter);
    Class<V> getSupportedType();
}
