package com.klinton.store.application.cart.update;

import com.klinton.store.domain.core.cart.Cart;
import com.klinton.store.domain.core.cart.CartGateway;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCartUseCaseTest {

    @InjectMocks
    public DefaultUpdateCartUseCase useCase;

    @Mock
    public CartGateway cartGateway;

    @Test
    public void givenAValidCommand_whenCallUpdate_thenShouldUpdateCart() {
        // Arrange
        final var cart = Cart.with("cart_id", "customer_id", "product_id", 1, 10.0);
        final var command = UpdateCartCommand.of("cart_id", 2, 20.0);
        when(cartGateway.findById("cart_id")).thenReturn(Optional.of(cart));
        when(cartGateway.save(cart)).thenAnswer(returnsFirstArg());

        // Act
        final var output = useCase.execute(command);

        // Assert
        verify(cartGateway, times(1)).save(argThat(c ->
                c.id().equals("cart_id") &&
                c.customerId().equals("customer_id") &&
                c.productId().equals("product_id") &&
                c.quantity() == 2 &&
                c.price() == 20.0
        ));
    }

    @Test
    public void givenAnInvalidCartId_whenCallUpdate_thenShouldThrowANotFoundException() {
        // Arrange
        final var command = UpdateCartCommand.of("cart_id", 2, 20.0);
        when(cartGateway.findById("cart_id")).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Cart with ID cart_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_thenShouldReturnTheException() {
        // Arrange
        final var cart = Cart.with("cart_id", "customer_id", "product_id", 1, 10.0);
        final var command = UpdateCartCommand.of("cart_id", 2, 20.0);
        final var expectedErrorMessage = "Gateway error";
        when(cartGateway.findById("cart_id")).thenReturn(Optional.of(cart));
        when(cartGateway.save(cart)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
