package com.klinton.store.infrastructure.admin.api;

import com.klinton.store.application.admin.create.CreateAdminCommand;
import com.klinton.store.application.admin.create.CreateAdminUseCase;
import com.klinton.store.application.admin.delete.DeleteAdminUseCase;
import com.klinton.store.application.admin.retrieve.get.GetAdminByIdUseCase;
import com.klinton.store.application.admin.retrieve.list.ListAdminsUseCase;
import com.klinton.store.application.admin.update.UpdateAdminCommand;
import com.klinton.store.application.admin.update.UpdateAdminUseCase;
import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;
import com.klinton.store.infrastructure.models.CreateAdminDto;
import com.klinton.store.infrastructure.models.GetAdminOutput;
import com.klinton.store.infrastructure.models.UpdateAdminDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class AdminController implements AdminApi {

    private final CreateAdminUseCase createAdminUseCase;

    private final ListAdminsUseCase listAdminsUseCase;

    private final GetAdminByIdUseCase getAdminByIdUseCase;

    private final UpdateAdminUseCase updateAdminUseCase;

    private final DeleteAdminUseCase deleteAdminUseCase;

    public AdminController(
            final CreateAdminUseCase createAdminUseCase,
            final ListAdminsUseCase listAdminsUseCase,
            final GetAdminByIdUseCase getAdminByIdUseCase,
            final UpdateAdminUseCase updateAdminUseCase,
            final DeleteAdminUseCase deleteAdminUseCase) {
        this.createAdminUseCase = Objects.requireNonNull(createAdminUseCase);
        this.listAdminsUseCase = Objects.requireNonNull(listAdminsUseCase);
        this.getAdminByIdUseCase = Objects.requireNonNull(getAdminByIdUseCase);
        this.updateAdminUseCase = Objects.requireNonNull(updateAdminUseCase);
        this.deleteAdminUseCase = Objects.requireNonNull(deleteAdminUseCase);
    }

    @Override
    public ResponseEntity<?> createAdmin(CreateAdminDto input) {
        final var createCommand = CreateAdminCommand.with(
                input.name(),
                input.email(),
                input.password(),
                true
        );
        final var admin = createAdminUseCase.execute(createCommand);
        return ResponseEntity.created(URI.create("/admin/" + admin.id())).body(admin);
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String direction) {
        final var query = SearchQuery.of(page, perPage, search, sort, direction);
        return listAdminsUseCase.execute(query);
    }

    @Override
    public GetAdminOutput getAdminById(String id) {
        return GetAdminOutput.from(getAdminByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateAdmin(String id, UpdateAdminDto input) {
        final var updateCommand = UpdateAdminCommand.with(
                id,
                input.name(),
                input.email(),
                input.active()
        );
        final var output = updateAdminUseCase.execute(updateCommand);

        return ResponseEntity.ok(output.id());
    }

    @Override
    public ResponseEntity<Void> deleteAdmin(String id) {
        deleteAdminUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
