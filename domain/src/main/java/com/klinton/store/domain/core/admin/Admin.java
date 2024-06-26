package com.klinton.store.domain.core.admin;

import com.klinton.store.domain.AggregateRoot;
import com.klinton.store.domain.validation.ValidationHandler;

import java.time.Instant;

public class Admin extends AggregateRoot<AdminID> {

    private String name;

    private String email;

    private String password;

    private boolean active;

    private final Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    protected Admin(
            final AdminID adminID,
            final String name,
            final String email,
            final String password,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(adminID);
        this.name = name;
        this.email = email;
        this.password = password;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Admin create(final String name, final String email, final String password, final boolean active) {
        final var id = AdminID.unique();
        final var now = Instant.now();
        final var deletedAt = active ? null : now;

        return new Admin(id, name, email, password, active, now, now, deletedAt);
    }

    public static Admin with(
            final AdminID adminID,
            final String name,
            final String email,
            final String password,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Admin(adminID, name, email, password, active, createdAt, updatedAt, deletedAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new AdminValidator(this, handler).validate();
    }

    public void update(final String name, final String email, final boolean active) {
        this.name = name;
        this.email = email;
        this.active = active;
        this.updatedAt = Instant.now();
        this.deletedAt = active ? null : this.deletedAt;
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = Instant.now();
        if (this.deletedAt == null) {
            this.deletedAt = Instant.now();
        }
    }

    public void activate() {
        this.active = true;
        this.updatedAt = Instant.now();
        this.deletedAt = null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
