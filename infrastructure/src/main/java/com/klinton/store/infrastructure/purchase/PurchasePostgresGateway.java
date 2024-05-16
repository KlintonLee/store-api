package com.klinton.store.infrastructure.purchase;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.purchase.persistence.PurchaseJpaEntity;
import com.klinton.store.infrastructure.purchase.persistence.PurchaseJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.klinton.store.infrastructure.utils.RepositoryUtils.like;

@Component
public class PurchasePostgresGateway implements PurchaseGateway {

    private final PurchaseJpaRepository purchaseRepository;

    public PurchasePostgresGateway(final PurchaseJpaRepository purchaseRepository) {
        this.purchaseRepository = Objects.requireNonNull(purchaseRepository);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(PurchaseJpaEntity.from(purchase)).toAggregate();
    }

    @Override
    public Optional<Purchase> getByID(PurchaseId purchaseID) {
        return purchaseRepository.findById(purchaseID.getValue()).map(PurchaseJpaEntity::toAggregate);
    }

    @Override
    public void delete(PurchaseId purchaseID) {
        purchaseRepository.deleteById(purchaseID.getValue());
    }

    @Override
    public Pagination<Purchase> getAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var whereCondition = Specification.where(
                like("paymentMethod", query.terms())
                .or(like("status", query.terms()
        )));

        final var pageResult = purchaseRepository.findAll(whereCondition, page);
        return Pagination.of(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(PurchaseJpaEntity::toAggregate).toList()
        );
    }
}
