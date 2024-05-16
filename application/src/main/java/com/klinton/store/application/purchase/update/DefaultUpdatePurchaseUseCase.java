package com.klinton.store.application.purchase.update;

import com.klinton.store.application.Utils;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;

import java.util.Objects;

public class DefaultUpdatePurchaseUseCase extends UpdatePurchaseUseCase {

    private final PurchaseGateway purchaseGateway;

    private final AddressGateway addressGateway;

    public DefaultUpdatePurchaseUseCase(final PurchaseGateway purchaseGateway, final AddressGateway addressGateway) {
        this.purchaseGateway = Objects.requireNonNull(purchaseGateway);
        this.addressGateway = Objects.requireNonNull(addressGateway);
    }

    @Override
    public UpdatePurchaseOutput execute(UpdatePurchaseCommand command) {
        final var purchaseId = PurchaseId.from(command.purchaseId());
        final var purchase = purchaseGateway.getByID(purchaseId)
                .orElseThrow(Utils.notFound(purchaseId, Purchase.class));

        final var addressId = AddressId.from(command.addressId());
        addressGateway.getById(addressId)
                .orElseThrow(Utils.notFound(addressId, Address.class));

        purchase.update(
                command.addressId(),
                command.totalPrice(),
                command.paymentMethod(),
                command.status()
        );

        return UpdatePurchaseOutput.from(purchaseGateway.save(purchase));
    }
}
