package com.klinton.store.infrastructure.products.api;

import com.klinton.store.application.product.create.CreateProductCommand;
import com.klinton.store.application.product.create.CreateProductUseCase;
import com.klinton.store.application.product.delete.DeleteProductUseCase;
import com.klinton.store.application.product.retrieve.get.GetProductByIdUseCase;
import com.klinton.store.application.product.retrieve.list.ListProductsUseCase;
import com.klinton.store.application.product.update.UpdateProductCommand;
import com.klinton.store.application.product.update.UpdateProductUseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.products.dto.CreateProductDto;
import com.klinton.store.infrastructure.products.dto.UpdateProductDto;
import com.klinton.store.infrastructure.products.presenter.GetProductResponse;
import com.klinton.store.infrastructure.products.presenter.ProductApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ProductController implements ProductApi {

    private final CreateProductUseCase createProductUseCase;

    private final ListProductsUseCase listProductsUseCase;

    private final GetProductByIdUseCase getProductByIdUseCase;

    private final UpdateProductUseCase updateProductUseCase;

    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(
            final CreateProductUseCase createProductUseCase,
            final ListProductsUseCase listProductsUseCase,
            final GetProductByIdUseCase getProductByIdUseCase,
            final UpdateProductUseCase updateProductUseCase,
            final DeleteProductUseCase deleteProductUseCase
    ) {
        this.createProductUseCase = createProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @Override
    public ResponseEntity<?> createProduct(CreateProductDto dto) {
        final var createCommand = CreateProductCommand.of(
                dto.name(),
                dto.description(),
                dto.quantity(),
                dto.price()
        );

        final var output = createProductUseCase.execute(createCommand);
        return ResponseEntity.created(URI.create("/products/" + output.id())).body(output);
    }

    @Override
    public Pagination<?> listProducts(String search, int page, int perPage, String sort, String direction) {
        final var query = SearchQuery.of(page, perPage, search, sort, direction);
        return listProductsUseCase.execute(query);
    }

    @Override
    public GetProductResponse getProductById(String id) {
        return ProductApiPresenter.present(getProductByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateProduct(String id, UpdateProductDto dto) {
        final var updateCommand = UpdateProductCommand.of(
                id,
                dto.name(),
                dto.description(),
                dto.quantity(),
                dto.price(),
                dto.active()
        );
        final var output = updateProductUseCase.execute(updateCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(String id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
