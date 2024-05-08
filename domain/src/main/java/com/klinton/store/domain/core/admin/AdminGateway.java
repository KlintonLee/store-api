package com.klinton.store.domain.core.admin;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface AdminGateway {
    Admin save(Admin admin);

    Optional<Admin> getById(AdminID adminID);

    void delete(AdminID adminID);

    Pagination<Admin> getAll(SearchQuery query);
}
