package com.klinton.store.application.purchase.retrieve.get;

import com.klinton.store.domain.core.purchase.Purchase;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.core.purchase.PurchaseId;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetPurchaseByIdUseCaseTest {

    @InjectMocks
    private DefaultGetPurchaseByIdUseCase useCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Test
    public void givenAValidPurchaseId_whenCallGetPurchaseByIdUseCase_thenShouldReturnPurchase() {
        // Arrange
        final var purchaseId = "valid_purchase_id";
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.ofNullable(mock(Purchase.class)));

        // Act
        assertDoesNotThrow(() -> useCase.execute(purchaseId));

        // Assert
        verify(purchaseGateway, times(1)).getByID(PurchaseId.from(purchaseId));
    }

    @Test
    public void givenAnInvalidPurchaseId_whenCallGetPurchaseByIdUseCase_thenShouldThrowNotFoundException() {
        // Arrange
        final var purchaseId = "invalid_purchase_id";
        when(purchaseGateway.getByID(PurchaseId.from(purchaseId))).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Purchase with ID invalid_purchase_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(purchaseId));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidPurchaseId_whenGatewayThrowsAnException_thenShouldReturnTheException() {
        // Arrange
        final var expectedErrorMessage = "Gateway Error";
        when(purchaseGateway.getByID(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute("123"));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
