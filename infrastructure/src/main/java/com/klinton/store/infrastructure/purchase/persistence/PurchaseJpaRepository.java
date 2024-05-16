package com.klinton.store.infrastructure.purchase.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseJpaRepository extends JpaRepository<PurchaseJpaEntity, String> {
   <T> Page<PurchaseJpaEntity> findAll(Specification<T> whereCondition, Pageable page);
}
