package com.klinton.store.application.cart.retrieve;

import com.klinton.store.domain.core.cart.Cart;
import com.klinton.store.domain.core.cart.CartGateway;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListCartByCustomerIdUseCaseTest {

    @InjectMocks
    public DefaultListCartByCustomerIdUseCase useCase;

    @Mock
    public CartGateway cartGateway;

    @Mock
    public ProductGateway productGateway;

    @Test
    public void givenAValidCustomerId_whenCallList_thenShouldReturnCarts() {
        // Arrange
        final var carts = List.of(
                Cart.with("cart_id_1", "customer_id_01", "product_id_1", 1, 100.0),
                Cart.with("cart_id_2", "customer_id_02","product_id_2", 10, 1000.0)
        );
        final var product_1 = Product.create("product_01", "product description 01", 100, 100.0, true);
        final var product_2 = Product.create("product_02", "product description 02", 50, 50.0, true);
        final var customerId = CustomerID.from("customer_id");
        when(cartGateway.findAllByCustomerId(customerId)).thenReturn(carts);
        when(productGateway.getById(any())).thenReturn(Optional.of(product_1), Optional.of(product_2));
        final var expected = List.of(
                ListCartByCustomerOutput.of("cart_id_1", product_1, 1, 100.0),
                ListCartByCustomerOutput.of("cart_id_2", product_2, 10, 1000.0)
        );

        // Act
        final var output = useCase.execute(customerId.getValue());

        // Assert
        assertEquals(expected, output);
    }
}
