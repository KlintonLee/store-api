package com.klinton.store.application.address.delete;

import com.klinton.store.domain.core.address.AddressGateway;
import com.klinton.store.domain.core.address.AddressId;
import com.klinton.store.domain.core.customer.CustomerGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteAddressUseCaseTest {

    @InjectMocks
    private DefaultDeleteAddressUseCase useCase;

    @Mock
    private AddressGateway addressGateway;

    @Mock
    private CustomerGateway customerGateway;

    @Test
    public void givenAValidAddressId_whenCallDeleteAddress_thenShouldDeleteAddress() {
        // Given
        final var addressId = AddressId.from( "any_address_id");
        doNothing().when(addressGateway).delete(addressId);

        // When
        useCase.execute(addressId.getValue());

        // Then
        verify(addressGateway, times(1)).delete(addressId);
    }
}
