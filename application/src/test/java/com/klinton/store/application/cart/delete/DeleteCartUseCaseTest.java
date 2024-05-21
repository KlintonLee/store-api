package com.klinton.store.application.cart.delete;

import com.klinton.store.domain.core.cart.CartGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCartUseCaseTest {

    @InjectMocks
    private DefaultDeleteCartUseCase deleteCartUseCase;

    @Mock
    private CartGateway cartGateway;

    @Test
    public void givenAValidCartId_whenCallDeleteCart_thenShouldDeleteIt() {
        // Arrange
        final var cartId = "cart_id";
        doNothing().when(cartGateway).delete(cartId);

        // Act
        deleteCartUseCase.execute(cartId);

        // Assert
        verify(cartGateway, times(1)).delete(cartId);
    }
}
