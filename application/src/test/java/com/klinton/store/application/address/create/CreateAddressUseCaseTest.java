package com.klinton.store.application.address.create;

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

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAddressUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    private final static String EXPECTED_STREET = "any_street";

    private final static String EXPECTED_CITY = "any_city";

    private final static String EXPECTED_NEIGHBORHOOD = "any_neighborhood";

    private final static States EXPECTED_STATE = States.AP;

    private final static String EXPECTED_NUMBER = "1A";

    private final static String EXPECTED_ZIP_CODE = "12345678";

    @InjectMocks
    private DefaultCreateAddressUseCase useCase;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidCommand_whenCallCreateAddress_thenShouldCreateAddress() {
        // Given
        final var customer = Customer.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        final var customerId = customer.getId();
        final var command = CreateAddressCommand.from(
                customerId.getValue(),
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                States.AP,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        when(customerGateway.getById(customerId)).thenReturn(Optional.of(customer));
        when(addressGateway.save(any())).thenAnswer(returnsFirstArg());
        // When
        useCase.execute(command);

        // Then
        verify(addressGateway, times(1)).save(argThat(address ->
                Objects.equals(address.getCustomerId(), customerId.getValue()) &&
                Objects.equals(address.getStreet(), EXPECTED_STREET) &&
                Objects.equals(address.getCity(), EXPECTED_CITY) &&
                Objects.equals(address.getNeighborhood(), EXPECTED_NEIGHBORHOOD) &&
                Objects.equals(address.getState(), States.AP) &&
                Objects.equals(address.getNumber(), EXPECTED_NUMBER) &&
                Objects.equals(address.getZipCode(), EXPECTED_ZIP_CODE
        )));
    }

    @Test
    public void givenACommandWithNonExistingCustomer_whenCallCreateAddress_thenShouldThrowNotFoundException() {
        // Given
        final var customerId = "non_existing_customer_id";
        final var command = CreateAddressCommand.from(
                customerId,
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                States.AP,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Customer with ID non_existing_customer_id was not found.";
        when(customerGateway.getById(any())).thenReturn(Optional.empty());
        // When
        final var exception = assertThrows(NotFoundException.class, () -> useCase.execute(command));

        // Then
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenACommandWithNullStreet_whenCallCreateAddress_thenShouldThrowUnprocessableEntityException() {
        // Given
        final var customer = Customer.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        final var customerId = customer.getId();
        final var command = CreateAddressCommand.from(
                customerId.getValue(),
                null,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                States.AP,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Street name should not be null or empty";

        // When
        final var exception = assertThrows(UnprocessableEntityException.class, () -> useCase.execute(command));

        // Then
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsUnexpectedException_thenShouldThrowException() {
        // Given
        final var customer = Customer.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECT_PASSWORD, EXPECT_PHONE);
        final var customerId = customer.getId();
        final var command = CreateAddressCommand.from(
                customerId.getValue(),
                EXPECTED_STREET,
                EXPECTED_CITY,
                EXPECTED_NEIGHBORHOOD,
                States.AP,
                EXPECTED_NUMBER,
                EXPECTED_ZIP_CODE
        );
        final var expectedErrorMessage = "Gateway Error";
        when(customerGateway.getById(customerId)).thenReturn(Optional.of(customer));
        when(addressGateway.save(any())).thenThrow(new IllegalStateException(expectedErrorMessage));
        // When
        final var exception = assertThrows(IllegalStateException.class, () -> useCase.execute(command));

        // Then
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
