package com.klinton.store.application.address.retrieve.get;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.address.States;
import com.klinton.store.domain.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAddressUseCaseTest {

    private final static String EXPECTED_STREET = "any_street";

    private final static String EXPECTED_CITY = "any_city";

    private final static String EXPECTED_NEIGHBORHOOD = "any_neighborhood";

    private final static States EXPECTED_STATE = States.AP;

    private final static String EXPECTED_NUMBER = "1A";

    private final static String EXPECTED_ZIP_CODE = "12345678";

    @InjectMocks
    private DefaultGetAddressByIdUseCase useCase;

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void givenAValidAddressId_whenCallGetAddress_thenShouldReturnAddress() {
        // Arrange
        final var address = Address.create(
                "any_customer_id",
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        when(addressGateway.getById(address.getId())).thenReturn(Optional.of(address));

        // Act
        final var result = useCase.execute(address.getId().getValue());

        // Assert
        assertEquals(address.getId().getValue(), result.id());
        assertEquals(address.getCustomerId(), result.customerId());
        assertEquals(EXPECTED_STREET, result.street());
        assertEquals(EXPECTED_NUMBER, result.number());
        assertEquals(EXPECTED_NEIGHBORHOOD, result.neighborhood());
        assertEquals(EXPECTED_CITY, result.city());
        assertEquals(EXPECTED_STATE, result.state());
        assertEquals(EXPECTED_ZIP_CODE, result.zipCode());
    }

    @Test
    public void givenANonExistingId_whenCallGetAddress_thenShouldThrowNotFoundException() {
        // Arrange
        final var addressId = AddressId.from( "non_existing_id");
        when(addressGateway.getById(addressId)).thenReturn(Optional.empty());
        final var expectedErrorMessage = "Address with ID non_existing_id was not found.";

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(addressId.getValue()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnTheException() {
        // Arrange
        final var addressId = AddressId.from( "any_address_id");
        final var expectedErrorMessage = "Gateway error";
        when(addressGateway.getById(addressId)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(addressId.getValue()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
