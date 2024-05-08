package com.klinton.store.domain.aggregate.cart;

import com.klinton.store.domain.Identifier;

import java.util.UUID;

public class CartId extends Identifier {

    private final String value;

    private CartId(String value) {
        this.value = value;
    }

    public static CartId from(String value) {
        return new CartId(value);
    }

    public static CartId from(UUID uuid) {
        return CartId.from(uuid.toString());
    }

    public static CartId unique() {
        return CartId.from(UUID.randomUUID());
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CartId that = (CartId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
