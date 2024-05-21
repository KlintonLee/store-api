package com.klinton.store.infrastructure.config;

import com.klinton.store.application.purchase.create.CreatePurchaseUseCase;
import com.klinton.store.application.purchase.create.DefaultCreatePurchaseUseCase;
import com.klinton.store.application.purchase.retrieve.get.DefaultGetPurchaseByIdUseCase;
import com.klinton.store.application.purchase.retrieve.get.GetPurchaseByIdUseCase;
import com.klinton.store.application.purchase.retrieve.list.DefaultListPurchasesUseCase;
import com.klinton.store.application.purchase.retrieve.list.ListPurchasesUseCase;
import com.klinton.store.application.purchase.update.DefaultUpdatePurchaseUseCase;
import com.klinton.store.application.purchase.update.UpdatePurchaseUseCase;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PurchaseUseCaseConfig {

    private final PurchaseGateway purchaseGateway;

    private final CustomerGateway customerGateway;

    private final AddressGateway addressGateway;

    public PurchaseUseCaseConfig(
            final PurchaseGateway purchaseGateway,
            final CustomerGateway customerGateway,
            final AddressGateway addressGateway
    ) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Bean
    public CreatePurchaseUseCase createPurchaseUseCase() {
        return new DefaultCreatePurchaseUseCase(purchaseGateway, customerGateway, addressGateway);
    }

    @Bean
    public ListPurchasesUseCase listPurchasesUseCase() {
        return new DefaultListPurchasesUseCase(purchaseGateway);
    }

    @Bean
    public GetPurchaseByIdUseCase getPurchaseByIdUseCase() {
        return new DefaultGetPurchaseByIdUseCase(purchaseGateway, customerGateway, addressGateway);
    }

    @Bean
    public UpdatePurchaseUseCase updatePurchaseUseCase() {
        return new DefaultUpdatePurchaseUseCase(purchaseGateway, addressGateway);
    }
}
