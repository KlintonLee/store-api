package com.klinton.store.infrastructure.address;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.address.persistence.AddressJpaEntity;
import com.klinton.store.infrastructure.address.persistence.AddressJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static com.klinton.store.infrastructure.utils.RepositoryUtils.like;

@Component
public class AddressPostgresGateway implements AddressGateway {

    private final AddressJpaRepository addressJpaRepository;

    public AddressPostgresGateway(final AddressJpaRepository addressJpaRepository) {
        this.addressJpaRepository = Objects.requireNonNull(addressJpaRepository);
    }

    @Override
    public Address save(Address address) {
        return addressJpaRepository.save(AddressJpaEntity.from(address)).toAggregate();
    }

    @Override
    public Optional<Address> getById(AddressId addressID) {
        return addressJpaRepository.findById(addressID.getValue()).map(AddressJpaEntity::toAggregate);
    }

    @Override
    public void delete(AddressId addressID) {
        addressJpaRepository.deleteById(addressID.getValue());
    }

    @Override
    public Pagination<Address> getAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );

        final var whereCondition = Specification.where(
                like("street", query.terms())
                .or(like("city", query.terms())
        ));

        return addressJpaRepository.findAll(whereCondition, page).map(AddressJpaEntity::toAggregate);
    }
}
