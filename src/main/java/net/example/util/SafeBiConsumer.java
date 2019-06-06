package net.example.util;

import java.util.function.BiConsumer;

public interface SafeBiConsumer<T, U> extends BiConsumer<T, U> {
    @Override
    default void accept(T ps, U data) {
        try {
            safeApply(ps, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void safeApply(T ps, U data) throws Exception;
}