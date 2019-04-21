package net.example.data.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegex {
    String regex();
    String message();

    class AnnotationProcessor implements ValidationAnnotationProcessor<String, ValidRegex> {
        @Override
        public Class<ValidRegex> getSupportedType() {
            return ValidRegex.class;
        }

        @Override
        public boolean isValid(String data, ValidRegex annotation) {
            return data.matches(annotation.regex());
        }

        @Override
        public String getMessage(ValidRegex annotation) {
            return annotation.message();
        }
    }
}
