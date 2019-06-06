package net.example.tranforemer;

import net.example.resolver.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransformationService {
    private Map<Class, Transformer> transformers = new HashMap<>();

    public TransformationService(List<Transformer> transformers) {
        transformers.forEach(t -> this.transformers.put(t.getSupportedType(), t));
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(HttpServletRequest request, Class<?> type, String name) {
        while (type != null) {
            if (transformers.containsKey(type)) {
                return (T) transformers.get(type).transform(request, name);
            }
            type = type.getSuperclass();
        }

        return null;
    }

    public <T> T transform(HttpServletRequest request, Class<?> type) {
        return transform(request, type, "");
    }
}
