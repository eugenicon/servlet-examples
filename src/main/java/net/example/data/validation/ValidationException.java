package net.example.data.validation;

import java.util.List;

public class ValidationException extends Exception {
    private List<String> errors;

    public ValidationException(List<String> errors) {
        super("Invalid data");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
