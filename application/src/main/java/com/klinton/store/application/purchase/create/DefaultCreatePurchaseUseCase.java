package com.klinton.store.application.purchase.create;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;

import java.util.Objects;

public class DefaultCreatePurchaseUseCase extends CreatePurchaseUseCase {

    private final PurchaseGateway purchaseGateway;

    private final CustomerGateway customerGateway;

    private final AddressGateway addressGateway;

    public DefaultCreatePurchaseUseCase(
            final PurchaseGateway purchaseGateway,
            final CustomerGateway customerGateway,
            final AddressGateway addressGateway
    ) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public CreatePurchaseOutput execute(CreatePurchaseCommand command) {
        final var customerId = CustomerID.from(command.customerId());
        customerGateway.getById(customerId)
                .orElseThrow(Utils.notFound(customerId, Customer.class));

        final var addressId = AddressId.from(command.addressId());
        addressGateway.getById(addressId)
                .orElseThrow(Utils.notFound(addressId, Address.class));

        final var purchase = Purchase.with(
                command.customerId(),
                command.addressId(),
                command.totalPrice(),
                command.paymentMethod(),
                command.status()
        );

        return CreatePurchaseOutput.with(purchaseGateway.save(purchase));
    }
}
