package com.klinton.store.domain.address;

import com.klinton.store.domain.exception.DomainException;
import com.klinton.store.domain.validation.ThrowValidationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

    private static final String STREET = "Rua dos bobos";
    private static final String CITY = "São Paulo";
    private static final States STATE = States.SP;
    private static final String NUMBER = "0";
    private static final String ZIPCODE = "00000-000";

    @Test
    public void givenValidParams_whenCallAddressCreate_thenShouldReturnAddressInstance() {
        // When
        final var address = Address.create(STREET, CITY, STATE, NUMBER, ZIPCODE);

        // Then
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(NUMBER, address.getNumber());
        assertEquals(ZIPCODE, address.getZipCode());
    }

    @Test
    public void givenNullStreet_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(null, CITY, STATE, NUMBER, ZIPCODE);
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
        final var address = Address.create(emptyStreet, CITY, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "Street name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullCity_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(STREET, null, STATE, NUMBER, ZIPCODE);
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
        final var address = Address.create(STREET, emptyCity, STATE, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "City name should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullState_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(STREET, CITY, null, NUMBER, ZIPCODE);
        final var expectedErrorMessage = "Brazilian state should should not be null";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenNullNumber_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(STREET, CITY, STATE, null, ZIPCODE);
        final var expectedErrorMessage = "Address number should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    @Test
    public void givenEmptyNumber_whenCallAddressValidate_thenShouldThrowDomainException() {
        // Arrange
        final var address = Address.create(STREET, CITY, STATE, null, ZIPCODE);
        final var expectedErrorMessage = "Address number should not be null or empty";

        // Act
        final var exception = assertThrows(DomainException.class, () -> address.validate(new ThrowValidationHandler()));

        // Assert
        assertEquals(expectedErrorMessage, exception.getMessage());
    }
}
