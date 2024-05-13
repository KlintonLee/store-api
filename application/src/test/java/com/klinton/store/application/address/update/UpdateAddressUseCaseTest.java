package com.klinton.store.application.address.update;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.States;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import com.klinton.store.domain.exception.NotFoundException;
import com.klinton.store.domain.exception.UnprocessableEntityException;
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
public class UpdateAddressUseCaseTest {
    private final static String EXPECTED_CUSTOMER_ID = "any_customer_id";

    private final static String EXPECTED_STREET = "any_street";

    private final static String EXPECTED_CITY = "any_city";

    private final static String EXPECTED_NEIGHBORHOOD = "any_neighborhood";

    private final static States EXPECTED_STATE = States.AP;

    private final static String EXPECTED_NUMBER = "1A";

    private final static String EXPECTED_ZIP_CODE = "12345678";

    @InjectMocks
    private DefaultUpdateAddressUseCase useCase;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidCommand_whenCallUpdateAddress_thenShouldUpdateAddress() {
        // Arrange
        final var address = Address.create(
                "123",
                "any_street",
                "any_city",
                "any_neighborhood",
                States.SP,
                "1A",
                "12345678"
        );
        final var addressId = address.getId();
        final var command = UpdateAddressCommand.from(
                addressId.getValue(),
                EXPECTED_CUSTOMER_ID,
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        when(addressGateway.getById(addressId)).thenReturn(Optional.of(address));
        when(customerGateway.getById(any())).thenReturn(Optional.of(mock(Customer.class)));
        when(addressGateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        final var output = useCase.execute(command);

        // Assert
        verify(addressGateway, times(1)).save(argThat(updatedAddress -> {
            assertEquals(EXPECTED_CUSTOMER_ID, updatedAddress.getCustomerId());
            assertEquals(EXPECTED_STREET, updatedAddress.getStreet());
            assertEquals(EXPECTED_CITY, updatedAddress.getCity());
            assertEquals(EXPECTED_NEIGHBORHOOD, updatedAddress.getNeighborhood());
            assertEquals(EXPECTED_STATE, updatedAddress.getState());
            assertEquals(EXPECTED_NUMBER, updatedAddress.getNumber());
            assertEquals(EXPECTED_ZIP_CODE, updatedAddress.getZipCode());
            return true;
        }));
    }

    @Test
    public void givenAnInvalidNullStreetCommand_whenCallUpdateAddress_thenShouldThrowUnprocessableEntityException() {
        // Arrange
        final var address = Address.create(
                "123",
                null,
                "any_city",
                "any_neighborhood",
                States.SP,
                "1A",
                "12345678"
        );
        final var addressId = address.getId();
        final var command = UpdateAddressCommand.from(
                addressId.getValue(),
                EXPECTED_CUSTOMER_ID,
                null,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Street name should not be null or empty";
        when(addressGateway.getById(addressId)).thenReturn(Optional.of(address));

        // Act
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenANonExistingAddressId_whenCallUpdateAddress_thenShouldThrowNotFoundException() {
        // Arrange
        final var address = Address.create(
                "123",
                "any_street",
                "any_city",
                "any_neighborhood",
                States.SP,
                "1A",
                "12345678"
        );
        final var addressId = address.getId();
        final var command = UpdateAddressCommand.from(
                addressId.getValue(),
                EXPECTED_CUSTOMER_ID,
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Address with ID %s was not found.".formatted(addressId.getValue());
        when(addressGateway.getById(addressId)).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenANonExistingCustomerId_whenCallUpdateAddress_thenShouldThrowNotFoundException() {
        // Arrange
        final var address = Address.create(
                "123",
                "any_street",
                "any_city",
                "any_neighborhood",
                States.SP,
                "1A",
                "12345678"
        );
        final var addressId = address.getId();
        final var command = UpdateAddressCommand.from(
                addressId.getValue(),
                EXPECTED_CUSTOMER_ID,
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Customer with ID any_customer_id was not found.";
        when(addressGateway.getById(addressId)).thenReturn(Optional.of(address));
        when(customerGateway.getById(any())).thenReturn(Optional.empty());

        // Act
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsException_shouldReturnTheException() {
        // Arrange
        final var address = Address.create(
                "123",
                "any_street",
                "any_city",
                "any_neighborhood",
                States.SP,
                "1A",
                "12345678"
        );
        final var addressId = address.getId();
        final var command = UpdateAddressCommand.from(
                addressId.getValue(),
                EXPECTED_CUSTOMER_ID,
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                EXPECTED_STATE,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Gateway error";
        when(addressGateway.getById(addressId)).thenThrow(new IllegalStateException(expectedErrorMessage));

        // Act
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
