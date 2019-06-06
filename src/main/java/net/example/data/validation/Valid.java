package net.example.data.validation;

import net.example.resolver.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
    Class<? extends Validator> value() default Validator.class;

    @Component
    class AnnotationProcessor implements ValidationAnnotationProcessor<Object, Valid> {
        private final Map<Object, Validator> validators;

        public AnnotationProcessor(List<Validator> validators) {
            this.validators = validators.stream().collect(Collectors.toMap(Validator::getClass, Function.identity()));
        }

        @Override
        public Class<Valid> getSupportedType() {
            return Valid.class;
        }

        @Override
        public boolean isValid(Object data, Valid annotation) {
            Validator validator = validators.get(annotation.value());
            return validator != null ? validator.isValid(data) : true;
        }

        @Override
        public String getMessage(Valid annotation) {
            Validator validator = validators.get(annotation.value());
            return validator != null ? validator.getMessage() : "";
        }
    }
}
