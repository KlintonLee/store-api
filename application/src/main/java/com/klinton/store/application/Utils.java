package com.klinton.store.application;

import com.klinton.store.domain.Identifier;
import com.klinton.store.domain.exception.NotFoundException;

import java.util.function.Supplier;

public class Utils {

    public static <T> Supplier<NotFoundException> notFound(Identifier anId, Class<T> clazz) {
        return () -> new NotFoundException("%s with ID %s was not found.".formatted(clazz.getSimpleName(), anId.getValue()));
    }
}
