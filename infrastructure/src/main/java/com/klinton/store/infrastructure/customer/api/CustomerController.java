package com.klinton.store.infrastructure.customer.api;

import com.klinton.store.application.customer.create.CreateCustomerCommand;
import com.klinton.store.application.customer.create.CreateCustomerUseCase;
import com.klinton.store.application.customer.delete.DeleteCustomerUseCase;
import com.klinton.store.application.customer.retrieve.get.GetCustomerByIdUseCase;
import com.klinton.store.application.customer.retrieve.list.ListCustomersUseCase;
import com.klinton.store.application.customer.update.UpdateCustomerCommand;
import com.klinton.store.application.customer.update.UpdateCustomerUseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.customer.dto.CreateCustomerDto;
import com.klinton.store.infrastructure.customer.dto.UpdateCustomerDto;
import com.klinton.store.infrastructure.customer.presenter.CustomerApiPresenter;
import com.klinton.store.infrastructure.customer.presenter.GetCustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class CustomerController implements CustomerApi {

    private final CreateCustomerUseCase createCustomerUseCase;

    private final ListCustomersUseCase listCustomersUseCase;

    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    private final UpdateCustomerUseCase updateCustomerUseCase;

    private final DeleteCustomerUseCase deleteCustomerUseCase;

    public CustomerController(
            final CreateCustomerUseCase createCustomerUseCase,
            final ListCustomersUseCase listCustomersUseCase,
            final GetCustomerByIdUseCase getCustomerByIdUseCase,
            final UpdateCustomerUseCase updateCustomerUseCase,
            final DeleteCustomerUseCase deleteCustomerUseCase

    ) {
        this.createCustomerUseCase = Objects.requireNonNull(createCustomerUseCase);
        this.listCustomersUseCase = Objects.requireNonNull(listCustomersUseCase);
        this.getCustomerByIdUseCase = Objects.requireNonNull(getCustomerByIdUseCase);
        this.updateCustomerUseCase = Objects.requireNonNull(updateCustomerUseCase);
        this.deleteCustomerUseCase = Objects.requireNonNull(deleteCustomerUseCase);
    }

    @Override
    public ResponseEntity<?> createCustomer(CreateCustomerDto input) {
        final var command = CreateCustomerCommand.of(
                input.name(),
                input.email(),
                input.password(),
                input.phone()
        );
        final var customer = createCustomerUseCase.execute(command);
        return ResponseEntity.created(URI.create("/customer/" + customer.id())).body(customer);
    }

    @Override
    public Pagination<?> listCustomers(String search, int page, int perPage, String sort, String direction) {
        final var query = SearchQuery.of(page, perPage, search, sort, direction);
        return listCustomersUseCase.execute(query);
    }

    @Override
    public GetCustomerResponse getCustomerById(String id) {
        return CustomerApiPresenter.present(getCustomerByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateCustomer(String id, UpdateCustomerDto input) {
        final var command = UpdateCustomerCommand.of(
                id,
                input.name(),
                input.email(),
                input.phone(),
                input.active()
        );
        return ResponseEntity.ok(updateCustomerUseCase.execute(command));
    }

    @Override
    public ResponseEntity<Void> deleteCustomer(String id) {
        deleteCustomerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
