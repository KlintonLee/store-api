package com.klinton.store.application.product.delete;

import com.klinton.store.domain.core.product.Product;
import com.klinton.store.domain.core.product.ProductGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

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
    public void givenAValidId_whenCallDeleteProductUseCase_thenShouldSoftDeleteIt() {
        // Arrange
        final var product = Product.create(EXPECTED_NAME, EXPECTED_DESCRIPTION, EXPECTED_QUANTITY, EXPECTED_PRICE, true);
        final var productId = product.getId();
        final var updatedAt = product.getUpdatedAt();
        when(productGateway.getById(any())).thenReturn(Optional.of(product));
        when(productGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
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
}
