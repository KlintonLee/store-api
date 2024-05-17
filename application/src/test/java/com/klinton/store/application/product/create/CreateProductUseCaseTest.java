package com.klinton.store.application.product.create;

import com.klinton.store.domain.core.product.ProductGateway;
import com.klinton.store.domain.exception.UnprocessableEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CreateProductUseCaseTest {

    private final String EXPECTED_NAME = "any_name";

    private final String EXPECTED_DESCRIPTION = "any_description";

    private final int EXPECTED_QUANTITY = 10;

    private final double EXPECTED_PRICE = 100.0;

    @InjectMocks
    private DefaultCreateProductUseCase createProductUseCase;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void givenAValidCommand_whenCallCreateProductUseCase_thenShouldCreateProduct() {
        // Arrange
        final var command = CreateProductCommand.of(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        when(productGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        final var output = createProductUseCase.execute(command);

        // Assert
        assertNotNull(output);
        verify(productGateway, times(1)).save(argThat(product ->
                Objects.equals(EXPECTED_NAME, product.getName()) &&
                Objects.equals(EXPECTED_DESCRIPTION, product.getDescription()) &&
                EXPECTED_QUANTITY == product.getQuantity() &&
                EXPECTED_PRICE == product.getPrice() &&
                Objects.nonNull(product.getCreatedAt()) &&
                Objects.nonNull(product.getUpdatedAt()) &&
                Objects.isNull(product.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithNullName_whenCallCreateProduct_thenShouldThrowUnprocessableException() {
        // Arrange
        final var command = CreateProductCommand.of(null, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        final var expectedErrorMessage = "Name should not be null or empty";

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> createProductUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsUnexpectedException_thenShouldThrowException() {
        // Arrange
        final var expectedErrorMessage = "Gateway Error";
        final var command = CreateProductCommand.of(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE);
        when(productGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> createProductUseCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
