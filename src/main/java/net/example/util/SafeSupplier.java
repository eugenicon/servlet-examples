package net.example.util;

import java.util.function.Supplier;

public interface SafeSupplier<T> extends Supplier<T> {
    @Override
    default T get() {
        try {
            return safeGet();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    T safeGet() throws Exception;
}