package com.klinton.store.domain.product;

import com.klinton.store.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ProductID extends Identifier {

    private final String value;

    private ProductID(final String value) {
        this.value = value;
    }

    public static ProductID unique() {
        return ProductID.of(UUID.randomUUID());
    }

    public static ProductID of(final UUID value) {
        return ProductID.of(value.toString());
    }

    public static ProductID of(final String value) {
        return new ProductID(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductID productID = (ProductID) o;
        return Objects.equals(getValue(), productID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
