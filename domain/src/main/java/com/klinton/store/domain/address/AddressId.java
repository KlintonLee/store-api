package com.klinton.store.domain.address;

import com.klinton.store.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class AddressId extends Identifier {

    private final String value;

    public AddressId(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static AddressId from(String id) {
        return new AddressId(id);
    }

    public static AddressId from(UUID uuid) {
        return AddressId.from(uuid.toString());
    }

    public static AddressId unique() {
        return AddressId.from(UUID.randomUUID());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AddressId addressId = (AddressId) o;
        return Objects.equals(getValue(), addressId.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
