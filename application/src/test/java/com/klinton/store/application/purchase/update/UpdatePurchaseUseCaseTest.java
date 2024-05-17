package com.klinton.store.application.purchase.update;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;
import com.klinton.store.domain.core.purchase.PurchaseStatus;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdatePurchaseUseCaseTest {

    private static final String CUSTOMER_ID = "customer_id";

    private static final String ADDRESS_ID = "address_id";

    private static final double TOTAL_PRICE = 10.0;

    private static final String PAYMENT_METHOD = "boleto";

    private static final PurchaseStatus STATUS = PurchaseStatus.CANCELED;

    @InjectMocks
    private DefaultUpdatePurchaseUseCase updatePurchaseUseCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void givenAValidCommand_whenCallUpdatePurchaseUseCase_thenShouldUpdatePurchase() {
        // Arrange
        final var purchase = Purchase.with(CUSTOMER_ID, "old_address", 5.0, "credit card", PurchaseStatus.PENDING);
        final var purchaseId = purchase.id();
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.of(purchase));
        when(addressGateway.getById(AddressId.from(ADDRESS_ID))).thenReturn(Optional.of(mock(Address.class)));
        when(purchaseGateway.save(any())).thenAnswer(returnsFirstArg());

        final var command = UpdatePurchaseCommand.of(
                purchaseId,
                ADDRESS_ID,
                TOTAL_PRICE,
                PAYMENT_METHOD,
                STATUS
        );

        // Act
        updatePurchaseUseCase.execute(command);

        // Assert
        verify(purchaseGateway, times(1)).save(argThat(purchaseUpdated ->
                Objects.equals(purchaseUpdated.id(), purchaseId) &&
                Objects.equals(purchaseUpdated.addressId(), ADDRESS_ID) &&
                purchaseUpdated.totalPrice() == TOTAL_PRICE &&
                Objects.equals(purchaseUpdated.paymentMethod(), PAYMENT_METHOD) &&
                Objects.equals(purchaseUpdated.status(), STATUS)
        ));
    }

    @Test
    public void givenAnInvalidPurchaseId_whenCallUpdatePurchaseUseCase_thenShouldThrowNotFoundException() {
        // Arrange
        final var purchase = Purchase.with(CUSTOMER_ID, "old_address", 5.0, "credit card", PurchaseStatus.PENDING);
        final var purchaseId = purchase.id();
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Purchase with ID %s was not found.".formatted(purchaseId);
        final var command = UpdatePurchaseCommand.of(
                purchaseId,
                ADDRESS_ID,
                TOTAL_PRICE,
                PAYMENT_METHOD,
                STATUS
        );

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> updatePurchaseUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAnInvalidAddressId_whenCallUpdatePurchaseUseCase_thenShouldThrowNotFoundException() {
        // Arrange
        final var purchase = Purchase.with(CUSTOMER_ID, "old_address", 5.0, "credit card", PurchaseStatus.PENDING);
        final var purchaseId = purchase.id();
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.of(purchase));
        when(addressGateway.getById(AddressId.from(ADDRESS_ID))).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Address with ID address_id was not found.";
        final var command = UpdatePurchaseCommand.of(
                purchaseId,
                ADDRESS_ID,
                TOTAL_PRICE,
                PAYMENT_METHOD,
                STATUS
        );

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> updatePurchaseUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_thenShouldReturnTheException() {
        // Arrange
        final var purchase = Purchase.with(CUSTOMER_ID, "old_address", 5.0, "credit card", PurchaseStatus.PENDING);
        final var purchaseId = purchase.id();
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.of(purchase));
        when(addressGateway.getById(AddressId.from(ADDRESS_ID))).thenReturn(Optional.of(mock(Address.class)));

        final var expectedErrorMessage = "Gateway Error";
        when(purchaseGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var command = UpdatePurchaseCommand.of(
                purchaseId,
                ADDRESS_ID,
                TOTAL_PRICE,
                PAYMENT_METHOD,
                STATUS
        );

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> updatePurchaseUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
