package com.klinton.store.application.cart.create;

import com.klinton.store.domain.core.cart.CartGateway;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCartUseCaseTest {

    private static final String CUSTOMER_ID = "customer_id";

    private static final String PRODUCT_ID = "product_id";

    private static final int QUANTITY = 1;

    private static final double PRICE = 100.0;

    @InjectMocks
    private DefaultCreateCartUseCase useCase;

    @Mock
    private CartGateway cartGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidCartCommand_whenCallCreateCart_thenShouldReturnItCreated() {
        // Arrange
        final var command = CreateCartCommand.of(CUSTOMER_ID, PRODUCT_ID, QUANTITY, PRICE);
        when(customerGateway.getById(any())).thenReturn(Optional.of(mock(Customer.class)));
        when(productGateway.getById(any())).thenReturn(Optional.of(mock(Product.class)));
        when(cartGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        useCase.execute(command);

        // Assert
        verify(cartGateway, times(1)).save(argThat(cart ->
                cart.customerId().equals(CUSTOMER_ID) &&
                cart.productId().equals(PRODUCT_ID) &&
                cart.quantity() == QUANTITY &&
                cart.price() == PRICE
        ));
    }

    @Test
    public void givenAnInvalidCustomerId_whenCallCreateCart_thenShouldThrowAnException() {
        // Arrange
        final var command = CreateCartCommand.of(CUSTOMER_ID, PRODUCT_ID, QUANTITY, PRICE);
        when(customerGateway.getById(any())).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Customer with ID customer_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAnInvalidProductId_whenCallCreateCart_thenShouldThrowAnException() {
        // Arrange
        final var command = CreateCartCommand.of(CUSTOMER_ID, PRODUCT_ID, QUANTITY, PRICE);
        when(customerGateway.getById(any())).thenReturn(Optional.of(mock(Customer.class)));
        when(productGateway.getById(any())).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Product with ID product_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
