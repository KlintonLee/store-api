package com.klinton.store.domain.aggregates.address;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.States;
import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    private static final String CUSTOMER_ID = "any_valid_id";
    private static final String STREET = "Rua dos bobos";
    private static final String CITY = "São Paulo";
    private static final String NEIGHBORHOOD = "Vila do Chaves";
    private static final States STATE = States.SP;
    private static final String NUMBER = "0";
    private static final String ZIPCODE = "00000-000";

    @Test
    public void givenValidParams_whenCallAddressCreate_thenShouldReturnAddressInstance() {
        // When
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);

        // Then
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(CUSTOMER_ID, address.getCustomerId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(NEIGHBORHOOD, address.getNeighborhood());
        assertEquals(STATE, address.getState());
        assertEquals(NUMBER, address.getNumber());
        assertEquals(ZIPCODE, address.getZipCode());
    }

    @Test
    public void givenNullStreet_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(CUSTOMER_ID, null, CITY, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "Street name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyStreet_whenCallAddressValidate_thenShouldThrownDomainException() {
        // Arrange
        final var emptyStreet = " ";
        final var address = Address.create(CUSTOMER_ID, emptyStreet, CITY, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "Street name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullCity_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(CUSTOMER_ID, STREET, null, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "City name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyCity_whenCallAddressValidate_thenShouldThrownDomainException() {
        // Arrange
        final var emptyCity = " ";
        final var address = Address.create(CUSTOMER_ID, STREET, emptyCity, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "City name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullState_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, null, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "Brazilian state should should not be null";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullNumber_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, null, ZIPCODE);
        final var expectedErrorMessage = "Address number should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyNumber_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var emptyNumber = " ";
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, emptyNumber, ZIPCODE);
        final var expectedErrorMessage = "Address number should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullZipCode_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, NUMBER, null);
        final var expectedErrorMessage = "Zip code should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyZipCode_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var emptyZipCode = " ";
        final var address = Address.create(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, NUMBER, emptyZipCode);
        final var expectedErrorMessage = "Zip code should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenValidParams_whenCallAddressUpdate_thenShouldReturnAddressInstance() {
        // Arrange
        final var address = Address.create(
                CUSTOMER_ID,
                "Rua abcd",
                "Rio de Janeiro",
                "Copacabana",
                States.RJ,
                "s/n",
                "11111-111"
        );

        // Act
        address.update(CUSTOMER_ID, STREET, CITY, NEIGHBORHOOD, STATE, NUMBER, ZIPCODE);

        // Assert
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(NEIGHBORHOOD, address.getNeighborhood());
        assertEquals(STATE, address.getState());
        assertEquals(NUMBER, address.getNumber());
        assertEquals(ZIPCODE, address.getZipCode());
    }
}
