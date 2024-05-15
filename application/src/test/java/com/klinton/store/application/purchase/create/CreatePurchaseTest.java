package com.klinton.store.application.purchase.create;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.purchase.PurchaseGateway;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreatePurchaseTest {

    private static final String CUSTOMER_ID = "customer_id";

    private static final String ADDRESS_ID = "address_id";

    private static final Instant PURCHASE_DATE = Instant.now();

    private static final double TOTAL_PRICE = 10.0;

    private static final String PAYMENT_METHOD = "boleto";

    @InjectMocks
    private DefaultCreatePurchaseUseCase createPurchaseUseCase;

    @Mock
    private PurchaseGateway purchaseGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void givenAValidCommand_whenCallCreatePurchase_thenShouldReturnPurchase() {
        // Arrange
        final var command = CreatePurchaseCommand.with(
                CUSTOMER_ID,
                ADDRESS_ID,
                PURCHASE_DATE,
                TOTAL_PRICE,
                PAYMENT_METHOD
        );
        when(purchaseGateway.save(any())).thenAnswer(returnsFirstArg());
        when(customerGateway.getById(any())).thenReturn(Optional.ofNullable(mock(Customer.class)));
        when(addressGateway.getById(any())).thenReturn(Optional.ofNullable(mock(Address.class)));

        // Act
        createPurchaseUseCase.execute(command);

        // Assert
        verify(purchaseGateway, times(1)).save(argThat(purchase ->
                Objects.equals(purchase.customerId(), CUSTOMER_ID) &&
                        Objects.equals(purchase.addressId(), ADDRESS_ID) &&
                        Objects.equals(purchase.purchaseDate(), PURCHASE_DATE) &&
                        purchase.totalPrice() == TOTAL_PRICE &&
                        Objects.equals(purchase.paymentMethod(), PAYMENT_METHOD)
        ));
    }

    @Test
    public void givenAnInvalidCustomerId_whenCallCreatePurchase_thenShouldReturnNotFoundException() {
        // Arrange
        final var command = CreatePurchaseCommand.with(
                CUSTOMER_ID,
                ADDRESS_ID,
                PURCHASE_DATE,
                TOTAL_PRICE,
                PAYMENT_METHOD
        );
        final var expectedErrorMessage = "Customer with ID customer_id was not found.";
        when(customerGateway.getById(any())).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> createPurchaseUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAnInvalidAddressId_whenCallCreatePurchase_thenShouldReturnNotFoundException() {
        // Arrange
        final var command = CreatePurchaseCommand.with(
                CUSTOMER_ID,
                ADDRESS_ID,
                PURCHASE_DATE,
                TOTAL_PRICE,
                PAYMENT_METHOD
        );
        final var expectedErrorMessage = "Address with ID address_id was not found.";
        when(customerGateway.getById(any())).thenReturn(Optional.ofNullable(mock(Customer.class)));
        when(addressGateway.getById(any())).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> createPurchaseUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
