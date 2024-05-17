package com.klinton.store.application.product.update;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.core.product.ProductID;
import com.klinton.store.domain.exception.NotFoundException;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateProductUseCaseTest {

    private final String EXPECTED_NAME = "any_name";

    private final String EXPECTED_DESCRIPTION = "any_description";

    private final int EXPECTED_QUANTITY = 10;

    private final double EXPECTED_PRICE = 100.0;

    private final boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultUpdateProductUseCase useCase;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateProductUseCase_thenShouldUpdateProduct() throws InterruptedException {
        // Arrange
        final var product = Product.create("other_name", "other_description", 15, 200.0, false);
        final var productId = product.getId();
        final var updatedAt = product.getUpdatedAt();
        when(productGateway.getById(productId)).thenReturn(Optional.of(product));
        when(productGateway.save(any())).thenAnswer(returnsFirstArg());
        final var command = UpdateProductCommand.of(
                product.getId().getValue(),
                EXPECTED_NAME,
                EXPECTED_DESCRIPTION,
                EXPECTED_QUANTITY,
                EXPECTED_PRICE,
                EXPECTED_ACTIVE
        );

        // Act
        Thread.sleep(1);
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        verify(productGateway, times(1)).save(argThat(updatedProduct ->
                updatedProduct.getId().equals(product.getId()) &&
                EXPECTED_NAME.equals(updatedProduct.getName()) &&
                EXPECTED_DESCRIPTION.equals(updatedProduct.getDescription()) &&
                EXPECTED_QUANTITY == updatedProduct.getQuantity() &&
                EXPECTED_PRICE == updatedProduct.getPrice() &&
                EXPECTED_ACTIVE == updatedProduct.isActive() &&
                updatedProduct.getCreatedAt().equals(product.getCreatedAt()) &&
                updatedAt.isBefore(updatedProduct.getUpdatedAt()) &&
                Objects.isNull(updatedProduct.getDeletedAt())
        ));
    }

    @Test
    public void givenAnInvalidNullName_whenCallUpdateProductUseCase_thenShouldThrowException() {
        // Arrange
        final var product = Product.create("other_name", "other_description", 15, 200.0, false);
        final var productId = product.getId();
        when(productGateway.getById(productId)).thenReturn(Optional.of(product));
        final var command = UpdateProductCommand.of(
                product.getId().getValue(),
                null,
                EXPECTED_DESCRIPTION,
                EXPECTED_QUANTITY,
                EXPECTED_PRICE,
                EXPECTED_ACTIVE
        );
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Assert
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenANonExistingProduct_whenCallUpdateProductUseCase_thenShouldThrowNotFoundException() {
        // Arrange
        final var productId = ProductID.from("invalid_id");
        when(productGateway.getById(productId)).thenReturn(Optional.empty());
        final var command = UpdateProductCommand.of(
                productId.getValue(),
                EXPECTED_NAME,
                EXPECTED_DESCRIPTION,
                EXPECTED_QUANTITY,
                EXPECTED_PRICE,
                EXPECTED_ACTIVE
        );
        final var expectedErrorMessage = "Product with ID invalid_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_thenShouldReturnTheException() throws InterruptedException {
        // Arrange
        final var expectedErrorMessage = "Gateway error";
        final var product = Product.create("other_name", "other_description", 15, 200.0, false);
        final var productId = product.getId();
        when(productGateway.getById(productId)).thenReturn(Optional.of(product));
        when(productGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));
        final var command = UpdateProductCommand.of(
                product.getId().getValue(),
                EXPECTED_NAME,
                EXPECTED_DESCRIPTION,
                EXPECTED_QUANTITY,
                EXPECTED_PRICE,
                EXPECTED_ACTIVE
        );

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertNotNull(exception);
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
