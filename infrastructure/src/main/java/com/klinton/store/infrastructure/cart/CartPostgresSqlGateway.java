package com.klinton.store.infrastructure.cart;

import com.klinton.store.domain.core.cart.Cart;
import com.klinton.store.domain.core.cart.CartGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.infrastructure.cart.persistence.CartJpaEntity;
import com.klinton.store.infrastructure.cart.persistence.CartJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CartPostgresSqlGateway implements CartGateway {

    private final CartJpaRepository cartJpaRepository;

    public CartPostgresSqlGateway(final CartJpaRepository cartJpaRepository) {
        this.cartJpaRepository = Objects.requireNonNull(cartJpaRepository);
    }

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(CartJpaEntity.from(cart)).toAggregate();
    }

    @Override
    public Optional<Cart> findById(String id) {
        return cartJpaRepository.findById(id).map(CartJpaEntity::toAggregate);
    }

    @Override
    public List<Cart> findAllByCustomerId(CustomerID id) {
        return cartJpaRepository.findAllByCustomerId(id.getValue()).stream().map(CartJpaEntity::toAggregate).toList();
    }

    @Override
    public void delete(String id) {
        final Optional<CartJpaEntity> cartJpaEntity = cartJpaRepository.findById(id);
        cartJpaEntity.ifPresent(cartJpaRepository::delete);
    }
}
