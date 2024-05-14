package com.klinton.store.infrastructure.address.persistence;

import com.klinton.store.domain.pagination.Pagination;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressJpaRepository extends JpaRepository<AddressJpaEntity, String> {

    <T> Pagination<AddressJpaEntity> findAll(Specification<T> whereClause, Pageable page);
}
