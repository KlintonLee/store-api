package com.klinton.store.infrastructure.products.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {

    <T> Page<ProductJpaEntity> findAll(Specification<T> whereClause, Pageable page);
}
