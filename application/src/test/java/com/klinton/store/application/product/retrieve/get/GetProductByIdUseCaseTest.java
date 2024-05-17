package com.klinton.store.application.product.retrieve.get;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetProductByIdUseCaseTest {

    private final String EXPECTED_NAME = "any_name";

    private final String EXPECTED_DESCRIPTION = "any_description";

    private final int EXPECTED_QUANTITY = 10;

    private final double EXPECTED_PRICE = 100.0;

    @InjectMocks
    private DefaultGetProductByIdUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidId_whenCallGetProductByIdUseCase_thenShouldReturnIt() {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, true);
        final var productId = product.getId();
        when(productGateway.getById(ProductID.from(productId.getValue()))).thenReturn(Optional.of(product));

        // Act
        final var output = useCase.execute(productId.getValue());

        // Assert
        assertNotNull(output);
        assertEquals(EXPECTED_NAME, output.name());
        assertEquals(EXPECTED_DESCRIPTION, output.description());
        assertEquals(EXPECTED_QUANTITY, output.quantity());
        assertEquals(EXPECTED_PRICE, output.price());
        assertTrue(output.active());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
        assertNull(output.deletedAt());
    }

    @Test
    public void givenANonExistingId_whenCallGetProductByIdUseCase_thenShouldReturnIt() {
        // Arrange
        final var expectedErrorMessage = "Product with ID invalid_id was not found.";
        when(productGateway.getById(any())).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute("invalid_id"));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnIt() {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, true);
        final var productId = product.getId();
        final var expectedErrorMessage = "Gateway error";
        when(productGateway.getById(ProductID.from(productId.getValue()))).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(productId.getValue()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
