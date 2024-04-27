package com.klinton.store.domain.users.admin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void givenValidParams_whenCallNewAdmin_thenShouldReturnAdmin() {
        // Given
        String name = "John Doe";
        String email = "john.doe@fake_email.com";
        String password = "123456";
        boolean active = true;

        // When
        final var admin = Admin.create(name, email, password, active);

        // Then
        assertNotNull(admin);
        assertNotNull(admin.getId());
        assertEquals(name, admin.getName());
        assertEquals(email, admin.getEmail());
        assertEquals(password, admin.getPassword());
        assertEquals(active, admin.isActive());
        assertNotNull(admin.getCreatedAt());
        assertNotNull(admin.getUpdatedAt());
        assertNull(admin.getDeletedAt());
    }
}
