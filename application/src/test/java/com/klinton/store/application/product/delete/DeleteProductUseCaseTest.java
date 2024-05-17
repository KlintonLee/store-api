package com.klinton.store.application.product.delete;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
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
public class DeleteProductUseCaseTest {

    private final String EXPECTED_NAME = "any_name";

    private final String EXPECTED_DESCRIPTION = "any_description";

    private final int EXPECTED_QUANTITY = 10;

    private final double EXPECTED_PRICE = 100.0;

    @InjectMocks
    private DefaultDeleteProductUseCase deleteProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidId_whenCallDeleteProductUseCase_thenShouldSoftDeleteIt() throws InterruptedException {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, true);
        final var productId = product.getId();
        final var updatedAt = product.getUpdatedAt();
        when(productGateway.getById(any())).thenReturn(Optional.of(product));
        when(productGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        Thread.sleep(1);
        deleteProductUseCase.execute(productId.getValue());

        // Assert
        verify(productGateway, times(1)).save(argThat(p ->
                Objects.equals(EXPECTED_NAME, p.getName()) &&
                Objects.equals(EXPECTED_DESCRIPTION, p.getDescription()) &&
                EXPECTED_QUANTITY == p.getQuantity() &&
                EXPECTED_PRICE == p.getPrice() &&
                !p.isActive() &&
                Objects.nonNull(p.getCreatedAt()) &&
                updatedAt.isBefore(p.getUpdatedAt()) &&
                Objects.nonNull(p.getDeletedAt())
        ));
    }

    @Test
    public void givenAnInvalidId_whenCallDeleteProductUseCase_thenShouldThrowANotFoundException() {
        // Arrange
        final var productId = "invalid_id";
        when(productGateway.getById(any())).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Product with ID invalid_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> deleteProductUseCase.execute(productId));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnTheException() {
        // Arrange
        final var expectedErrorMessage = "Gateway error";
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, true);
        final var productId = product.getId().getValue();
        when(productGateway.getById(any())).thenReturn(Optional.of(product));
        when(productGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> deleteProductUseCase.execute(productId));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
