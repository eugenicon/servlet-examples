package net.example.tranforemer;

import net.example.resolver.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransformationService {
    private static final Logger LOGGER = LogManager.getLogger(TransformationService.class);
    private Map<Class, Transformer> transformers = new HashMap<>();

    public TransformationService(List<Transformer> transformers) {
        transformers.forEach(t -> this.transformers.put(t.getSupportedType(), t));
    }

    @SuppressWarnings("unchecked")
    public <T> T transform(HttpServletRequest request, Class<?> type, String name) {
        while (type != null) {
            if (transformers.containsKey(type)) {
                try {
                    return (T) transformers.get(type).transform(request, name);
                } catch (Exception e) {
                    LOGGER.error("{}: {}", transformers.get(type).getClass().getSimpleName(), e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            type = type.getSuperclass();
        }

        return null;
    }

    public <T> T transform(HttpServletRequest request, Class<?> type) {
        return transform(request, type, "");
    }
}
