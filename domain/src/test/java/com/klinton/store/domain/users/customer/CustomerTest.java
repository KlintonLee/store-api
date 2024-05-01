package com.klinton.store.domain.users.customer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void givenAValidParams_whenCallingCreate_thenShouldReturnACustomer() {
        // Given
        var name = "John Doe";
        var email = "johndoe@email.com";
        var password = "hashed_password";
        var phone = "1234567890";
        var customer = Customer.create(name, email, password, phone);

        assertNotNull(customer);
        assertNotNull(customer.getId());
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(password, customer.getPassword());
        assertEquals(phone, customer.getPhone());
        assertTrue(customer.isActive());
        assertNotNull(customer.getCreatedAt());
        assertNotNull(customer.getUpdatedAt());
        assertNull(customer.getDeletedAt());
    }
}
