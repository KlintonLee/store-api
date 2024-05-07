package com.klinton.store.domain.aggregate.customer;

import com.klinton.store.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CustomerID extends Identifier {

    private final String id;

    @Override
    public String getValue() {
        return id;
    }

    private CustomerID(String id) {
        this.id = id;
    }

    public static CustomerID unique() {
        return from(UUID.randomUUID());
    }

    public static CustomerID from(UUID id) {
        return from(id.toString());
    }

    public static CustomerID from(String id) {
        return new CustomerID(id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CustomerID that = (CustomerID) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
