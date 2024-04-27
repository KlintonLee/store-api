package com.klinton.store.domain.users.admin;

import com.klinton.store.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class AdminID extends Identifier {

    private String value;

    public AdminID(String value) {
        this.value = value;
    }

    public static AdminID unique() {
        return from(UUID.randomUUID().toString());
    }

    public static AdminID from(UUID value) {
        return from(value.toString());
    }

    public static AdminID from(String value) {
        return new AdminID(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AdminID adminID = (AdminID) o;
        return Objects.equals(getValue(), adminID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
