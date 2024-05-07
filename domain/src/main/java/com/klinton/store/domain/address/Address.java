package com.klinton.store.domain.address;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

public class Address extends AggregateRoot<AddressId> {

    private String street;

    private String city;

    private String state;

    private String number;

    private String zipCode;

    protected Address(
            final AddressId addressId,
            final String street,
            final String city,
            final String state,
            final String number,
            final String zipCode
    ) {
        super(addressId);
        this.street = street;
        this.city = city;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
    }

    public static Address create(
            final String street,
            final String city,
            final String state,
            final String number,
            final String zipCode
    ) {
        var addressId = AddressId.unique();
        return new Address(addressId, street, city, state, number, zipCode);
    }

    @Override
    public void validate(ValidationHandler handler) {

    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }

    public String getZipCode() {
        return zipCode;
    }
}
