package com.klinton.store.infrastructure.config;

import com.klinton.store.application.customer.create.CreateCustomerUseCase;
import com.klinton.store.application.customer.create.DefaultCreateCustomerUseCase;
import com.klinton.store.application.customer.delete.DefaultDeleteCustomerUseCase;
import com.klinton.store.application.customer.delete.DeleteCustomerUseCase;
import com.klinton.store.application.customer.retrieve.get.DefaultGetCustomerByIdUseCase;
import com.klinton.store.application.customer.retrieve.get.GetCustomerByIdUseCase;
import com.klinton.store.application.customer.retrieve.list.DefaultListCustomersUseCase;
import com.klinton.store.application.customer.retrieve.list.ListCustomersUseCase;
import com.klinton.store.application.customer.update.DefaultUpdateCustomerUseCase;
import com.klinton.store.application.customer.update.UpdateCustomerUseCase;
import com.klinton.store.domain.core.customer.CustomerGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CustomerUseCaseConfig {

    private final CustomerGateway customerGateway;

    public CustomerUseCaseConfig(final CustomerGateway customerGateway) {
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new DefaultCreateCustomerUseCase(customerGateway);
    }

    @Bean
    public ListCustomersUseCase listCustomersUseCase() {
        return new DefaultListCustomersUseCase(customerGateway);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new DefaultGetCustomerByIdUseCase(customerGateway);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase() {
        return new DefaultUpdateCustomerUseCase(customerGateway);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase() {
        return new DefaultDeleteCustomerUseCase(customerGateway);
    }
}
