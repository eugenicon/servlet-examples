package net.example.util;

import java.util.function.Consumer;

public interface SafeConsumer<T> extends Consumer<T> {
    @Override
    default void accept(T data) {
        try {
            safeApply(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void safeApply(T preparedStatement) throws Exception;
}