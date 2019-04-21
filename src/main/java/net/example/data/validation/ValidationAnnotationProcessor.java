package net.example.data.validation;

import java.lang.annotation.Annotation;

public interface ValidationAnnotationProcessor<T, A extends Annotation> {
    Class<A> getSupportedType();

    boolean isValid(T data, A annotation);

    String getMessage(A annotation);
}
