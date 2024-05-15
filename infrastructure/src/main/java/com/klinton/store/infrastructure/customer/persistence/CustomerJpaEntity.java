package com.klinton.store.infrastructure.customer.persistence;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.customer.Customer;
import com.klinton.store.domain.core.customer.CustomerID;
import com.klinton.store.infrastructure.address.persistence.AddressJpaEntity;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Table(name = "customers")
@Entity(name = "Customer")
public class CustomerJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "customerId", fetch = FetchType.EAGER)
    private List<AddressJpaEntity> addresses;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "TIMESTAMP")
    private Instant deletedAt;

    public CustomerJpaEntity() {}

    private CustomerJpaEntity(
            final String id,
            final String name,
            final String email,
            final String password,
            final String phone,
            final boolean active,
            final List<AddressJpaEntity> addresses,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static CustomerJpaEntity from(Customer customer) {
        return new CustomerJpaEntity(
                customer.getId().getValue(),
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getPhone(),
                customer.isActive(),
                List.of(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getDeletedAt()
        );
    }

    public Customer toAggregate() {
        final var addresses = getAddresses() == null ? List.<Address>of() : getAddresses()
                .stream()
                .map(AddressJpaEntity::toAggregate)
                .toList();
        return Customer.with(
                CustomerID.from(getId()),
                getName(),
                getEmail(),
                getPassword(),
                getPhone(),
                isActive(),
                addresses,
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }

    public List<AddressJpaEntity> getAddresses() {
        return addresses;
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
