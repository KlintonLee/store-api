package com.klinton.store.domain.aggregate.media;

import com.klinton.store.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class ImageMedia extends ValueObject {

    private final String id;

    private final String checksum;

    private final String name;

    private final String location;

    private ImageMedia(
            final String id,
            final String checksum,
            final String name,
            final String location
    ) {
        this.id = Objects.requireNonNull(id);
        this.checksum = Objects.requireNonNull(checksum);
        this.name = Objects.requireNonNull(name);
        this.location = Objects.requireNonNull(location);
    }

    public static ImageMedia with(final String checksum, final String name, final String location) {
        var uuid = UUID.randomUUID().toString();
        return new ImageMedia(uuid, checksum, name, location);
    }

    public static ImageMedia with(final String id, final String checksum, final String name, final String location) {
        return new ImageMedia(id, checksum, name, location);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ImageMedia that = (ImageMedia) o;
        return Objects.equals(id, that.id) && Objects.equals(checksum, that.checksum) && Objects.equals(name, that.name) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checksum, name, location);
    }

    public String id() {
        return id;
    }

    public String checksum() {
        return checksum;
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }
}