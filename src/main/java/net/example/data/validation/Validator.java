package net.example.data.validation;

public interface Validator<T> {
    boolean isValid(T data);

    String getMessage();
}
