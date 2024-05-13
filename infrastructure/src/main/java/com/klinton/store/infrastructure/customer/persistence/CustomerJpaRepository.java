package com.klinton.store.infrastructure.customer.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, String> {

    <T> Page<CustomerJpaEntity> findAll(Specification<T> whereCondition, Pageable page);
}
