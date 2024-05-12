package com.klinton.store.infrastructure.customer;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.customer.persistence.CustomerJpaRepository;
import com.klinton.store.infrastructure.customer.persistence.CustomerJpaEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CustomerPostgresSqlGateway implements CustomerGateway {

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerPostgresSqlGateway(final CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = Objects.requireNonNull(customerJpaRepository);
    }

    @Override
    public Customer save(Customer customer) {
        return customerJpaRepository.save(CustomerJpaEntity.from(customer)).toAggregate();
    }

    @Override
    public Optional<Customer> getById(CustomerID customerID) {
        return customerJpaRepository.findById(customerID.getValue()).map(CustomerJpaEntity::toAggregate);
    }

    @Override
    public void delete(CustomerID customerID) {
        customerJpaRepository.deleteById(customerID.getValue());
    }

    @Override
    public Pagination<Customer> getAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var whereCondition = Specification.where(
                like("name", query.terms())
                .or(like("email", query.terms())
        ));

        final var pageResult = customerJpaRepository.findAll(whereCondition, page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalPages(),
                pageResult.map(CustomerJpaEntity::toAggregate).toList()
        );
    }

    private <T> Specification<T> like(final String columnName, final String term) {
        if (term == null) return null;

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(columnName)),
                "%" + term.toUpperCase() + "%");
    }
}
