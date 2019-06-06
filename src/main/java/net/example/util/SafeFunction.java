package net.example.util;

import java.util.function.Function;

public interface SafeFunction<T, R> extends Function<T, R> {
    @Override
    default R apply(T data) {
        try {
            return safeApply(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    R safeApply(T resultSet) throws Exception;
}