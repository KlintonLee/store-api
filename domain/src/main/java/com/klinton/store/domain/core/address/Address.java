package com.klinton.store.domain.core.address;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

public class Address extends AggregateRoot<AddressId> {

    private String customerId;

    private String street;

    private String city;

    private String neighborhood;

    private States state;

    private String number;

    private String zipCode;

    protected Address(
            final AddressId addressId,
            final String customerId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        super(addressId);
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
    }

    public static Address create(
            final String customerId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        var addressId = AddressId.unique();
        return new Address(addressId, customerId, street, city, neighborhood, state, number, zipCode);
    }

    public static Address with(
            final AddressId addressId,
            final String customerId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        return new Address(addressId, customerId, street, city, neighborhood, state, number, zipCode);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new AddressValidator(this, handler).validate();
    }

    public Address update(
            final String customerId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public States getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }

    public String getZipCode() {
        return zipCode;
    }
}
