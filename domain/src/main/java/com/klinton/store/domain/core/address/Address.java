package com.klinton.store.domain.core.address;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

public class Address extends AggregateRoot<AddressId> {

    private String street;

    private String city;

    private String neighborhood;

    private States state;

    private String number;

    private String zipCode;

    protected Address(
            final AddressId addressId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        super(addressId);
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
    }

    public static Address create(
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        var addressId = AddressId.unique();
        return new Address(addressId, street, city, neighborhood, state, number, zipCode);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new AddressValidator(this, handler).validate();
    }

    public Address update(
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
        return this;
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
