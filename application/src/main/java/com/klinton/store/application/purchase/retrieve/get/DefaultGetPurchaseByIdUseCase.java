package com.klinton.store.application.purchase.retrieve.get;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;

import java.util.Objects;

public class DefaultGetPurchaseByIdUseCase extends GetPurchaseByIdUseCase {

    private final PurchaseGateway purchaseGateway;

    private final CustomerGateway customerGateway;

    private final AddressGateway addressGateway;

    public DefaultGetPurchaseByIdUseCase(
            final PurchaseGateway purchaseGateway,
            final CustomerGateway customerGateway,
            final AddressGateway addressGateway
    ) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }


    @Override
    public GetPurchaseByIdOutput execute(String id) {
        final var purchaseId = PurchaseId.from(id);
        final var purchase = purchaseGateway.getByID(purchaseId)
                .orElseThrow(Utils.notFound(purchaseId, Purchase.class));

        final var customerId = CustomerID.from(purchase.customerId());
        final var customer = customerGateway.getById(customerId).orElse(null);

        final var addressId = AddressId.from(purchase.addressId());
        final var address = addressGateway.getById(addressId).orElse(null);

        return GetPurchaseByIdOutput.from(purchase, customer, address);
    }
}
