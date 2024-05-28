package com.klinton.store.infrastructure.cart.api;

import com.klinton.store.application.cart.create.CreateCartCommand;
import com.klinton.store.application.cart.create.CreateCartUseCase;
import com.klinton.store.application.cart.delete.DeleteCartUseCase;
import com.klinton.store.application.cart.retrieve.ListCartByCustomerIdUseCase;
import com.klinton.store.application.cart.update.UpdateCartCommand;
import com.klinton.store.application.cart.update.UpdateCartUseCase;
import com.klinton.store.infrastructure.cart.dto.CreateCartDto;
import com.klinton.store.infrastructure.cart.dto.UpdateCartDto;
import com.klinton.store.infrastructure.cart.presenter.GetCartsResponse;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class CartController implements CartApi {

    private final CreateCartUseCase createCartUseCase;

    private final ListCartByCustomerIdUseCase listCartByCustomerIdUseCase;

    private final UpdateCartUseCase updateCartUseCase;

    private final DeleteCartUseCase deleteCartUseCase;

    public CartController(
            final CreateCartUseCase createCartUseCase,
            final ListCartByCustomerIdUseCase listCartByCustomerIdUseCase,
            final UpdateCartUseCase updateCartUseCase,
            final DeleteCartUseCase deleteCartUseCase
    ) {
        this.createCartUseCase = Objects.requireNonNull(createCartUseCase);
        this.listCartByCustomerIdUseCase = Objects.requireNonNull(listCartByCustomerIdUseCase);
        this.updateCartUseCase = Objects.requireNonNull(updateCartUseCase);
        this.deleteCartUseCase = Objects.requireNonNull(deleteCartUseCase);
    }

    @Override
    public ResponseEntity<?> createCart(CreateCartDto dto) {
        final var command = CreateCartCommand.of(dto.customerId(), dto.productId(), dto.quantity(), dto.price());
        final var cart = createCartUseCase.execute(command);
        return ResponseEntity.created(URI.create("/carts/" + cart.id())).body(cart);
    }

    @Override
    public List<GetCartsResponse> getCartByCustomerId(String id) {
        return listCartByCustomerIdUseCase.execute(id).stream().map(GetCartsResponse::of).toList();
    }

    @Override
    public ResponseEntity<?> updateCart(String id, UpdateCartDto dto) {
        final var command = UpdateCartCommand.of(id, dto.quantity(), dto.price());
        return ResponseEntity.ok(updateCartUseCase.execute(command));
    }

    @Override
    public ResponseEntity<?> deleteCart(String id) {
        deleteCartUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
