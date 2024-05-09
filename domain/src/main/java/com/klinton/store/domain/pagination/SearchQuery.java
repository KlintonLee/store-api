package com.klinton.store.domain.pagination;

public record SearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {

    public static SearchQuery of(int page, int perPage, String terms, String sort, String direction) {
        return new SearchQuery(page, perPage, terms, sort, direction);
    }
}

