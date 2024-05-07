package com.klinton.store.domain.aggregate.admin;

import com.klinton.store.domain.pagination.Pagination;
import com.klinton.store.domain.pagination.SearchQuery;

import java.util.Optional;

public interface AdminGateway {
    Admin create(Admin admin);

    Optional<Admin> getById(AdminID adminID);

    Admin update(Admin admin);

    void delete(AdminID adminID);

    Pagination<Admin> getAll(SearchQuery query);
}
