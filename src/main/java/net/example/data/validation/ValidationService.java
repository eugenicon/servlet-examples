package net.example.data.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

public class ValidationService {
    private static final Logger LOGGER = Logger.getLogger(ValidationService.class.getName());
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private Map<Class<? extends Annotation>, ValidationAnnotationProcessor<Object, Annotation>> validators = new HashMap<>();

    public ValidationService(ValidationAnnotationProcessor... validators) {
        for (ValidationAnnotationProcessor<Object, Annotation> validator : validators) {
            this.validators.put(validator.getSupportedType(), validator);
        }
    }

    public <T> List<String> validate(T data) {
        ArrayList<String> validationErrors = new ArrayList<>();
        Method[] declaredMethods = data.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                Class<? extends Annotation> type = annotation.annotationType();
                if (validators.containsKey(type)) {
                    ValidationAnnotationProcessor<Object, Annotation> annotationProcessor = validators.get(type);
                    try {
                        if (!annotationProcessor.isValid(method.invoke(data), annotation)) {
                            validationErrors.add(resourceBundle.getString(annotationProcessor.getMessage(annotation)));
                        }
                    } catch (Exception e) {
                        LOGGER.warning(e.getMessage());
                        validationErrors.add(resourceBundle.getString("validation-exception"));
                    }
                }
            }
        }
        return validationErrors;
    }
}
