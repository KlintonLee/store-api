package com.klinton.store.application;

import com.klinton.store.domain.Identifier;
import com.klinton.store.domain.exception.NotFoundException;
import com.klinton.store.domain.validation.Error;
import com.klinton.store.domain.validation.Notification;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Utils {

    public static <T> Supplier<NotFoundException> notFound(Identifier anId, Class<T> clazz) {
        return () -> new NotFoundException("%s with ID %s was not found.".formatted(clazz.getSimpleName(), anId.getValue()));
    }

    public static String mountErrorMessage(Notification notification) {
        return notification.getErrors()
                .stream()
                .map(Error::message)
                .collect(Collectors.joining("; "));
    }
}
