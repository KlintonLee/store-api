package com.klinton.store.application.customer.create;

import com.klinton.store.domain.core.customer.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCustomerUseCaseTest {

    @InjectMocks
    private DefaultCreateCustomerUseCase useCase;

    @Mock
    private CustomerGateway gateway;

    @Test
    public void givenValidCommand_whenCallCreateCustomerUseCase_thenShouldReturnCustomer() {
        // Arrange
        final var name = "John Doe";
        final var email = "johndoe@email.com";
        final var password = "123456";
        final var phone = "123456789";
        final var active = true;
        final var command = new CreateCustomerCommand(name, email, password, phone);
        when(gateway.save(any())).thenAnswer(returnsFirstArg());

        // Act
        useCase.execute(command);

        // Assert
        verify(gateway, times(1)).save(argThat(customer ->
                Objects.equals(customer.getName(), name) &&
                Objects.equals(customer.getEmail(), email) &&
                Objects.equals(customer.getPassword(), password) &&
                Objects.equals(customer.getPhone(), phone) &&
                customer.isActive() &&
                Objects.nonNull(customer.getCreatedAt()) &&
                Objects.nonNull(customer.getUpdatedAt()) &&
                Objects.isNull(customer.getDeletedAt()
        )));
    }
}
