package com.klinton.store.domain.aggregate.purchase;

import com.klinton.store.domain.Identifier;

import java.util.Objects;

public class PurchaseId extends Identifier {

    private final String value;

    private PurchaseId(final String value) {
        this.value = value;
    }

    public static PurchaseId unique() {
        return PurchaseId.from(java.util.UUID.randomUUID());
    }

    public static PurchaseId from(final java.util.UUID value) {
        return PurchaseId.from(value.toString());
    }

    public static PurchaseId from(final String value) {
        return new PurchaseId(value);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseId that = (PurchaseId) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
