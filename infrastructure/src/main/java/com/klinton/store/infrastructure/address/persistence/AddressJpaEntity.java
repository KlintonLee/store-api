package com.klinton.store.infrastructure.address.persistence;

import com.klinton.store.domain.core.address.Address;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.address.States;
import jakarta.persistence.*;

@Entity(name = "Address")
@Table(name = "addresses")
public class AddressJpaEntity {

    @Id
    private String id;

    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private States state;

    @Column(name = "number")
    private String number;

    @Column(name = "zip_code")
    private String zipCode;

    public AddressJpaEntity() {}

    private AddressJpaEntity(
            final String id,
            final String customerId,
            final String street,
            final String city,
            final String neighborhood,
            final States state,
            final String number,
            final String zipCode
    ) {
        this.id = id;
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.number = number;
        this.zipCode = zipCode;
    }

    public static AddressJpaEntity from(Address address) {
        return new AddressJpaEntity(
                address.getId().getValue(),
                address.getCustomerId(),
                address.getStreet(),
                address.getCity(),
                address.getNeighborhood(),
                address.getState(),
                address.getNumber(),
                address.getZipCode()
        );
    }

    public Address toAggregate() {
        return Address.with(
                AddressId.from(getId()),
                getCustomerId(),
                getStreet(),
                getCity(),
                getNeighborhood(),
                getState(),
                getNumber(),
                getZipCode()
        );
    }

    public String getId() {
        return id;
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
