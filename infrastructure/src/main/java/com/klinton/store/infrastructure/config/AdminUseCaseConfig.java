package com.klinton.store.infrastructure.config;

import com.klinton.store.application.admin.create.CreateAdminUseCase;
import com.klinton.store.application.admin.create.DefaultCreateAdminUseCase;
import com.klinton.store.application.admin.delete.DefaultDeleteAdminUseCase;
import com.klinton.store.application.admin.delete.DeleteAdminUseCase;
import com.klinton.store.application.admin.retrieve.get.DefaultGetAdminByIdUseCase;
import com.klinton.store.application.admin.retrieve.get.GetAdminByIdUseCase;
import com.klinton.store.application.admin.retrieve.list.DefaultListAdminsUseCase;
import com.klinton.store.application.admin.retrieve.list.ListAdminsUseCase;
import com.klinton.store.application.admin.update.DefaultUpdateAdminUseCase;
import com.klinton.store.application.admin.update.UpdateAdminUseCase;
import com.klinton.store.domain.core.admin.AdminGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class AdminUseCaseConfig {

    private final AdminGateway adminGateway;

    public AdminUseCaseConfig(final AdminGateway adminGateway) {
        this.adminGateway = Objects.requireNonNull(adminGateway);
    }

    @Bean
    public CreateAdminUseCase createAdminUseCase() {
        return new DefaultCreateAdminUseCase(adminGateway);
    }

    @Bean
    public ListAdminsUseCase listAdminsUseCase() {
        return new DefaultListAdminsUseCase(adminGateway);
    }

    @Bean
    public GetAdminByIdUseCase getAdminByIdUseCase() {
        return new DefaultGetAdminByIdUseCase(adminGateway);
    }

    @Bean
    public UpdateAdminUseCase updateAdminUseCase() {
        return new DefaultUpdateAdminUseCase(adminGateway);
    }

    @Bean
    public DeleteAdminUseCase deleteAdminUseCase() {
        return new DefaultDeleteAdminUseCase(adminGateway);
    }
}
