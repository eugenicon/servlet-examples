package net.example.tranforemer;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TransformationService {
    private Map<Class, Function<HttpServletRequest, Object>> transformers = new HashMap<>();

    public TransformationService(Transformer... transformers) {
        for (Transformer transformer : transformers) {
            this.transformers.put(transformer.getSupportedType(), transformer::transform);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(HttpServletRequest request, Class type) {
        while (type != null) {
            if (transformers.containsKey(type)) {
                return (T) transformers.get(type).apply(request);
            }
            type = type.getSuperclass();
        }

        return null;
    }

    public void register(Class type, Function<HttpServletRequest, Object> transformation) {
        transformers.put(type, transformation);
    }
}
