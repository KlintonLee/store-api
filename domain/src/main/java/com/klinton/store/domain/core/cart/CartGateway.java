package com.klinton.store.domain.core.cart;

import com.klinton.store.domain.core.customer.CustomerID;

import java.util.List;
import java.util.Optional;

public interface CartGateway {

    Cart save(Cart cart);

    Optional<Cart> findById(String id);

    List<Cart> findAllByCustomerId(CustomerID id);

    void delete(String id);
}
