package com.klinton.store.infrastructure.admin.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, String> {
    <T> Page<AdminJpaEntity> findAll(Specification<T> whereClause, Pageable page);
}
