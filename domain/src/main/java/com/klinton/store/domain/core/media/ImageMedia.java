package com.klinton.store.domain.core.media;

import com.klinton.store.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class ImageMedia extends ValueObject {

    private final String id;

    private final String productId;

    private final String location;

    private final String contentType;

    private ImageMedia(
            final String id,
            final String productId,
            final String location,
            final String contentType
    ) {
        this.id = Objects.requireNonNull(id);
        this.productId = Objects.requireNonNull(productId);
        this.location = Objects.requireNonNull(location);
        this.contentType = Objects.requireNonNull(contentType);
    }

    public static ImageMedia with(final String productId, final String location, final String contentType) {
        var uuid = UUID.randomUUID().toString();
        return new ImageMedia(uuid, productId, location, contentType);
    }

    public static ImageMedia with(final String id, final String productId, final String location, final String contentType) {
        return new ImageMedia(id, productId, location, contentType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageMedia that = (ImageMedia) o;
        return Objects.equals(id, that.id) && Objects.equals(productId, that.productId) && Objects.equals(location, that.location) && Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, location, contentType);
    }

    public String id() {
        return id;
    }

    public String productId() {
        return productId;
    }

    public String location() {
        return location;
    }

    public String contentType() {
        return contentType;
    }
}
