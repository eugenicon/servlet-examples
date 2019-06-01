package net.example.data.validation;

import net.example.resolver.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
    String value();

    @Component
    class AnnotationProcessor implements ValidationAnnotationProcessor<Object, NotEmpty> {
        @Override
        public Class<NotEmpty> getSupportedType() {
            return NotEmpty.class;
        }

        @Override
        public boolean isValid(Object data, NotEmpty annotation) {
            return data != null && !String.valueOf(data).isEmpty();
        }

        @Override
        public String getMessage(NotEmpty annotation) {
            return annotation.value();
        }
    }
}