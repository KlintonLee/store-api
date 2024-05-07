package com.klinton.store.domain.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressTest {

    private static final String STREET = "Rua dos bobos";
    private static final String CITY = "São Paulo";
    private static final String STATE = "SP";
    private static final String NUMBER = "0";
    private static final String ZIPCODE = "00000-000";

    @Test
    public void givenValidParams_whenCallAddressCreate_thenShouldReturnAddressInstance() {
        // When
        var address = Address.create(STREET, CITY, STATE, NUMBER, ZIPCODE);

        // Then
        assertNotNull(address);
        assertNotNull(address.getId());
        assertEquals(STREET, address.getStreet());
        assertEquals(CITY, address.getCity());
        assertEquals(STATE, address.getState());
        assertEquals(NUMBER, address.getNumber());
        assertEquals(ZIPCODE, address.getZipCode());
    }
}
