package com.klinton.store.infrastructure.cart.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, String> {
    List<CartJpaEntity> findAllByCustomerId(String id);
}
