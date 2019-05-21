package net.example.tranforemer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class TransformationService {
    private Map<Class, BiFunction<HttpServletRequest, Parameter, Object>> transformers = new HashMap<>();

    public TransformationService(Transformer... transformers) {
        for (Transformer transformer : transformers) {
            this.transformers.put(transformer.getSupportedType(), transformer::transform);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(HttpServletRequest request, Parameter parameter) {
        Class<?> type = parameter.getType();
        while (type != null) {
            if (transformers.containsKey(type)) {
                return (T) transformers.get(type).apply(request, parameter);
            }
            type = type.getSuperclass();
        }

        return null;
    }

    public void register(Class type, BiFunction<HttpServletRequest, Parameter, Object> transformation) {
        transformers.put(type, transformation);
    }
}
