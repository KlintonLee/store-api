package com.klinton.store.infrastructure.admin;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import com.klinton.store.domain.core.admin.AdminID;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.admin.persistence.AdminJpaEntity;
import com.klinton.store.infrastructure.admin.persistence.AdminJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class AdminPostgresSqlGateway implements AdminGateway {

    private final AdminJpaRepository adminJpaRepository;

    public AdminPostgresSqlGateway(final AdminJpaRepository adminJpaRepository) {
        this.adminJpaRepository = Objects.requireNonNull(adminJpaRepository);
    }


    @Override
    public Admin save(Admin admin) {
        return adminJpaRepository.save(AdminJpaEntity.from(admin)).toAggregate();
    }

    @Override
    public Optional<Admin> getById(AdminID adminID) {
        return adminJpaRepository.findById(adminID.getValue()).map(AdminJpaEntity::toAggregate);
    }

    @Override
    public void delete(AdminID adminID) {
        adminJpaRepository.deleteById(adminID.getValue());
    }

    @Override
    public Pagination<Admin> getAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var whereCondition = Specification.where(
                like("name", query.terms())
                .or(like("email", query.terms())
        ));

        final var pageResult = adminJpaRepository.findAll(whereCondition, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(AdminJpaEntity::toAggregate).toList()
        );
    }

    private Specification<AdminJpaEntity> like(final String columnName, final String term) {
        if (term == null) return null;

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(columnName)),
                "%" + term.toUpperCase() + "%");
    }
}
