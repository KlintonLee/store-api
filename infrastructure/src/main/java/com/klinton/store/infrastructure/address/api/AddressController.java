package com.klinton.store.infrastructure.address.api;

import com.klinton.store.application.address.create.CreateAddressCommand;
import com.klinton.store.application.address.create.CreateAddressUseCase;
import com.klinton.store.application.address.delete.DeleteAddressUseCase;
import com.klinton.store.application.address.retrieve.get.GetAddressByIdUseCase;
import com.klinton.store.application.address.retrieve.list.ListAddressesUseCase;
import com.klinton.store.application.address.update.UpdateAddressCommand;
import com.klinton.store.application.address.update.UpdateAddressUseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.address.dto.CreateAddressDto;
import com.klinton.store.infrastructure.address.dto.UpdateAddressDto;
import com.klinton.store.infrastructure.address.presenter.AddressPresenter;
import com.klinton.store.infrastructure.address.presenter.GetAddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class AddressController implements AddressApi {

    private final CreateAddressUseCase createAddressUseCase;

    private final ListAddressesUseCase listAddressesUseCase;

    private final GetAddressByIdUseCase getAddressByIdUseCase;

    private final UpdateAddressUseCase updateAddressUseCase;

    private final DeleteAddressUseCase deleteAddressUseCase;

    public AddressController(
            final CreateAddressUseCase createAddressUseCase,
            final ListAddressesUseCase listAddressesUseCase,
            final GetAddressByIdUseCase getAddressByIdUseCase,
            final UpdateAddressUseCase updateAddressUseCase,
            final DeleteAddressUseCase deleteAddressUseCase
    ) {
        this.createAddressUseCase = Objects.requireNonNull(createAddressUseCase);
        this.listAddressesUseCase = Objects.requireNonNull(listAddressesUseCase);
        this.getAddressByIdUseCase = Objects.requireNonNull(getAddressByIdUseCase);
        this.updateAddressUseCase = Objects.requireNonNull(updateAddressUseCase);
        this.deleteAddressUseCase = Objects.requireNonNull(deleteAddressUseCase);
    }


    @Override
    public ResponseEntity<?> createAddress(CreateAddressDto input) {
        final var command = CreateAddressCommand.of(
                input.customerId(),
                input.street(),
                input.city(),
                input.neighborhood(),
                input.state(),
                input.number(),
                input.zipCode()
        );
        final var addressOutput = createAddressUseCase.execute(command);
        return ResponseEntity.created(URI.create("/address/" + addressOutput.id())).body(addressOutput);
    }

    @Override
    public Pagination<?> listAddresses(String search, int page, int perPage, String sort, String direction) {
        final var query = SearchQuery.of(page, perPage, search, sort, direction);
        return listAddressesUseCase.execute(query);
    }

    @Override
    public GetAddressResponse getAddressById(String id) {
        final var addressOutput = getAddressByIdUseCase.execute(id);
        return AddressPresenter.present(addressOutput);
    }

    @Override
    public ResponseEntity<?> updateAddress(String id, UpdateAddressDto input) {
        final var command = UpdateAddressCommand.of(
                id,
                input.customerId(),
                input.street(),
                input.city(),
                input.neighborhood(),
                input.state(),
                input.number(),
                input.zipCode()
        );
        final var addressOutput = updateAddressUseCase.execute(command);
        return ResponseEntity.ok(addressOutput);
    }

    @Override
    public ResponseEntity<?> deleteAddress(String id) {
        deleteAddressUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
