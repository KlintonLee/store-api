package com.klinton.store.infrastructure.purchase.api;

import com.klinton.store.application.purchase.create.CreatePurchaseCommand;
import com.klinton.store.application.purchase.create.CreatePurchaseUseCase;
import com.klinton.store.application.purchase.retrieve.get.GetPurchaseByIdUseCase;
import com.klinton.store.application.purchase.retrieve.list.ListPurchasesUseCase;
import com.klinton.store.application.purchase.update.UpdatePurchaseCommand;
import com.klinton.store.application.purchase.update.UpdatePurchaseUseCase;
import com.klinton.store.domain.core.purchase.PurchaseStatus;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.purchase.dto.CreatePurchaseDto;
import com.klinton.store.infrastructure.purchase.dto.UpdatePurchaseDto;
import com.klinton.store.infrastructure.purchase.presenter.GetPurchaseResponse;
import com.klinton.store.infrastructure.purchase.presenter.PurchaseApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class PurchaseController implements PurchaseApi {

    private final CreatePurchaseUseCase createPurchaseUseCase;

    private final ListPurchasesUseCase listPurchasesUseCase;

    private final GetPurchaseByIdUseCase getPurchaseByIdUseCase;

    private final UpdatePurchaseUseCase updatePurchaseUseCase;

    public PurchaseController(
            final CreatePurchaseUseCase createPurchaseUseCase,
            final ListPurchasesUseCase listPurchasesUseCase,
            final GetPurchaseByIdUseCase getPurchaseByIdUseCase,
            final UpdatePurchaseUseCase updatePurchaseUseCase
    ) {
        this.createPurchaseUseCase = Objects.requireNonNull(createPurchaseUseCase);
        this.listPurchasesUseCase = Objects.requireNonNull(listPurchasesUseCase);
        this.getPurchaseByIdUseCase = Objects.requireNonNull(getPurchaseByIdUseCase);
        this.updatePurchaseUseCase = Objects.requireNonNull(updatePurchaseUseCase);
    }


    @Override
    public ResponseEntity<?> createPurchase(CreatePurchaseDto dto) {
        final var command = CreatePurchaseCommand.with(
                dto.customerId(),
                dto.addressId(),
                dto.totalPrice(),
                dto.paymentMethod(),
                PurchaseStatus.valueOf(dto.status())
        );
        final var purchase = createPurchaseUseCase.execute(command);
        return ResponseEntity.created(URI.create("/purchases/" + purchase.id())).body(purchase);
    }

    @Override
    public Pagination<?> listPurchases(String search, int page, int perPage, String sort, String direction) {
        final var query = SearchQuery.of(page, perPage, search, sort, direction);
        return listPurchasesUseCase.execute(query);
    }

    @Override
    public GetPurchaseResponse getPurchaseById(String id) {
        return PurchaseApiPresenter.from(getPurchaseByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updatePurchase(String id, UpdatePurchaseDto dto) {
        final var command = UpdatePurchaseCommand.with(
                id,
                dto.addressId(),
                dto.totalPrice(),
                dto.paymentMethod(),
                PurchaseStatus.valueOf(dto.status())

        );
        final var purchase = updatePurchaseUseCase.execute(command);
        return ResponseEntity.ok(purchase);
    }
}
