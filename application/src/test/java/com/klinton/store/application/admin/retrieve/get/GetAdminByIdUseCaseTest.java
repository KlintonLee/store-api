package com.klinton.store.application.admin.retrieve.get;

import com.klinton.store.domain.core.admin.Admin;
import com.klinton.store.domain.core.admin.AdminGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetAdminByIdUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECTED_PASSWORD = "123456";

    private final static boolean EXPECTED_ACTIVE = true;

    @InjectMocks
    private DefaultGetAdminByIdUseCase useCase;

    @Mock
    private AdminGateway adminGateway;

    @Test
    public void givenAValidId_whenCallGetById_thenShouldReturnAdmin() {
        // Arrange
        final var admin = Admin.create(EXPECTED_NAME, EXPECTED_EMAIL, EXPECTED_PASSWORD, EXPECTED_ACTIVE);
        when(adminGateway.getById(admin.getId())).thenReturn(Optional.of(admin));

        // Act
        final var output = useCase.execute(admin.getId().getValue());

        // Assert
        assertNotNull(output);
        assertEquals(EXPECTED_NAME, output.name());
        assertEquals(EXPECTED_EMAIL, output.email());
        assertEquals(EXPECTED_PASSWORD, output.password());
        assertEquals(EXPECTED_ACTIVE, output.active());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
        assertNull(output.deletedAt());
    }

}
