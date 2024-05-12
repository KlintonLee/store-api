package com.klinton.store.application.customer.update;

import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCustomerUseCaseTest {

    private final static String EXPECTED_NAME = "John Doe";

    private final static String EXPECTED_EMAIL = "john.doe@fake_email.com";

    private final static String EXPECT_PASSWORD = "123456";

    private final static String EXPECT_PHONE = "123456789";

    @InjectMocks
    private DefaultUpdateCustomerUseCase useCase;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidCommand_whenCallUpdate_thenShouldUpdateCustomer() {
        // Arrange
        final var customer = Customer.create("JANE DOE", "jane.doe@fake_email.com", "654321", "987654321");
        final var customerId = customer.getId();
        final var createdAt = customer.getCreatedAt();
        final var updatedAt = customer.getUpdatedAt();
        final var command = UpdateCustomerCommand.with(
                customerId.getValue(),
                EXPECTED_NAME,
                EXPECTED_EMAIL,
                EXPECT_PASSWORD,
                EXPECT_PHONE,
                false
        );

        when(customerGateway.getById(customer.getId())).thenReturn(Optional.of(customer));
        when(customerGateway.save(customer)).thenAnswer(returnsFirstArg());

        // Act
        final var output = useCase.execute(command);

        // Assert
        assertNotNull(output);
        verify(customerGateway, times(1)).save(argThat(aCustomer ->
                Objects.equals(EXPECTED_NAME, aCustomer.getName()) &&
                Objects.equals(EXPECTED_EMAIL, aCustomer.getEmail()) &&
                Objects.equals(EXPECT_PASSWORD, aCustomer.getPassword()) &&
                Objects.equals(EXPECT_PHONE, aCustomer.getPhone()) &&
                !aCustomer.isActive() &&
                Objects.equals(createdAt, aCustomer.getCreatedAt()) &&
                updatedAt.isBefore(aCustomer.getUpdatedAt()) &&
                aCustomer.getDeletedAt() != null
        ));
    }
}
