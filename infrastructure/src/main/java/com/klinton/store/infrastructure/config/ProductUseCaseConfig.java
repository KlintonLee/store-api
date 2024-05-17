package com.klinton.store.infrastructure.config;

import com.klinton.store.application.product.create.CreateProductUseCase;
import com.klinton.store.application.product.create.DefaultCreateProductUseCase;
import com.klinton.store.application.product.delete.DefaultDeleteProductUseCase;
import com.klinton.store.application.product.delete.DeleteProductUseCase;
import com.klinton.store.application.product.retrieve.get.DefaultGetProductByIdUseCase;
import com.klinton.store.application.product.retrieve.get.GetProductByIdUseCase;
import com.klinton.store.application.product.retrieve.list.DefaultListProductsUseCase;
import com.klinton.store.application.product.retrieve.list.ListProductsUseCase;
import com.klinton.store.application.product.update.DefaultUpdateProductUseCase;
import com.klinton.store.application.product.update.UpdateProductUseCase;
import com.klinton.store.domain.core.product.ProductGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ProductUseCaseConfig {

    private final ProductGateway productGateway;

    public ProductUseCaseConfig(final ProductGateway productGateway) {
        this.productGateway = Objects.requireNonNull(productGateway);
    }

    @Bean
    public CreateProductUseCase createProductUseCase() {
        return new DefaultCreateProductUseCase(productGateway);
    }

    @Bean
    public ListProductsUseCase listProductsUseCase() {
        return new DefaultListProductsUseCase(productGateway);
    }

    @Bean
    public GetProductByIdUseCase getProductByIdUseCase() {
        return new DefaultGetProductByIdUseCase(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase() {
        return new DefaultUpdateProductUseCase(productGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase() {
        return new DefaultDeleteProductUseCase(productGateway);
    }
}
