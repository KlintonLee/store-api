package com.klinton.store.infrastructure.config;

import com.klinton.store.application.address.create.CreateAddressUseCase;
import com.klinton.store.application.address.create.DefaultCreateAddressUseCase;
import com.klinton.store.application.address.delete.DefaultDeleteAddressUseCase;
import com.klinton.store.application.address.delete.DeleteAddressUseCase;
import com.klinton.store.application.address.retrieve.get.DefaultGetAddressByIdUseCase;
import com.klinton.store.application.address.retrieve.get.GetAddressByIdUseCase;
import com.klinton.store.application.address.retrieve.list.DefaultListAddressesUseCase;
import com.klinton.store.application.address.retrieve.list.ListAddressesUseCase;
import com.klinton.store.application.address.update.DefaultUpdateAddressUseCase;
import com.klinton.store.application.address.update.UpdateAddressUseCase;
import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.customer.CustomerGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class AddressUseCaseConfig {

    private final AddressGateway addressGateway;

    private final CustomerGateway customerGateway;

    public AddressUseCaseConfig(final AddressGateway addressGateway, final CustomerGateway customerGateway) {
        this.addressGateway = Objects.requireNonNull(addressGateway);
        this.customerGateway = Objects.requireNonNull(customerGateway);
    }

    @Bean
    public CreateAddressUseCase createAddressUseCase() {
        return new DefaultCreateAddressUseCase(addressGateway, customerGateway);
    }

    @Bean
    public ListAddressesUseCase listAddressesUseCase() {
        return new DefaultListAddressesUseCase(addressGateway);
    }

    @Bean
    public GetAddressByIdUseCase getAddressUseCase() {
        return new DefaultGetAddressByIdUseCase(addressGateway);
    }

    @Bean
    public UpdateAddressUseCase updateAddressUseCase() {
        return new DefaultUpdateAddressUseCase(addressGateway, customerGateway);
    }

    @Bean
    public DeleteAddressUseCase deleteAddressUseCase() {
        return new DefaultDeleteAddressUseCase(addressGateway);
    }
}
