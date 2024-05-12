package com.klinton.store.infrastructure.utils;

import org.springframework.data.jpa.domain.Specification;

public class RepositoryUtils {

    public static <T> Specification<T> like(final String columnName, final String term) {
        if (term == null) return null;

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.upper(root.get(columnName)),
                "%" + term.toUpperCase() + "%");
    }
}
