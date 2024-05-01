package com.klinton.store.domain.users.customer;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

import java.time.Instant;

public class Customer extends AggregateRoot<CustomerID> {

    private String name;

    private String email;

    private String password;

    private String phone;

    private boolean active;

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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Customer create(String name, String email, String password, String phone) {
        var customerId = CustomerID.unique();
        var now = Instant.now();

        return new Customer(customerId, name, email, password, phone, true, now, now, null);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CustomerValidator(handler, this).validate();
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