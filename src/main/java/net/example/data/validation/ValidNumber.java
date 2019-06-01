package net.example.data.validation;

import net.example.resolver.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNumber {
    int min() default Integer.MIN_VALUE;
    int max() default Integer.MAX_VALUE;
    String message();

    @Component
    class AnnotationProcessor implements ValidationAnnotationProcessor<Number, ValidNumber> {
        @Override
        public Class<ValidNumber> getSupportedType() {
            return ValidNumber.class;
        }

        @Override
        public boolean isValid(Number data, ValidNumber annotation) {
            return data.intValue() >= annotation.min() && data.intValue() <= annotation.max();
        }

        @Override
        public String getMessage(ValidNumber annotation) {
            return annotation.message();
        }
    }
}