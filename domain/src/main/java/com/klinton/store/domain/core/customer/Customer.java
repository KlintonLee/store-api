package com.klinton.store.domain.core.customer;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Customer extends AggregateRoot<CustomerID> {

    private String name;

    private String email;

    private String password;

    private String phone;

    private boolean active;

    private List<Address> addresses;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    protected Customer(
            CustomerID customerID,
            final String name,
            final String email,
            final String password,
            final String phone,
            final boolean active,
            final List<Address> addresses,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(customerID);
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.active = active;
        this.addresses = addresses;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Customer create(String name, String email, String password, String phone) {
        var customerId = CustomerID.unique();
        var now = Instant.now();

        return new Customer(
                customerId,
                name,
                email,
                password,
                phone,
                true,
                new ArrayList<Address>(),
                now,
                now,
                null
        );
    }

    public static Customer with(
            CustomerID customerID,
            final String name,
            final String email,
            final String password,
            final String phone,
            final boolean active,
            final List<Address> addresses,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Customer(
                customerID,
                name,
                email,
                password,
                phone,
                active,
                addresses,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CustomerValidator(handler, this).validate();
    }

    public void deactivate() {
        var now = Instant.now();
        this.updatedAt = now;
        if (this.deletedAt == null) {
            this.deletedAt = now;
        }

        this.active = false;
    }

    public void activate() {
        this.updatedAt = Instant.now();;
        this.deletedAt = null;
        this.active = true;
    }

    public void update(String name, String email, String phone, boolean active) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        if (active) {
            activate();
        } else {
            deactivate();
        }
        this.updatedAt = Instant.now();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
