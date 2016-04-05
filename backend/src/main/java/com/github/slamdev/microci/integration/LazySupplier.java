package com.github.slamdev.microci.integration;

import java.util.function.Supplier;

public final class LazySupplier {

    private LazySupplier() {
        // Utility class
    }

    public static <T> Supplier<T> lazily(Supplier<T> supplier) {
        return new Supplier<T>() {
            private T value;

            @Override
            public T get() {
                if (value == null) {
                    value = supplier.get();
                }
                return value;
            }
        };
    }
}
