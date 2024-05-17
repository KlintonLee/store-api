package com.klinton.store.infrastructure.products;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.products.persistence.ProductJpaEntity;
import com.klinton.store.infrastructure.products.persistence.ProductJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.klinton.store.infrastructure.utils.RepositoryUtils.like;

@Component
public class ProductPostgresGateway implements ProductGateway {

    private final ProductJpaRepository repository;

    public ProductPostgresGateway(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(ProductJpaEntity.from(product)).toAggregate();
    }

    @Override
    public Optional<Product> getById(ProductID productID) {
        return repository.findById(productID.getValue()).map(ProductJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Product> getAll(SearchQuery query) {
        final var pageRequest = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var whereCondition = Specification.where(
                like("name", query.terms())
                .or(like("description", query.terms())
        ));

        final var pageResult = repository.findAll(whereCondition, pageRequest);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ProductJpaEntity::toAggregate).toList()
        );
    }
}
