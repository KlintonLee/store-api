package com.klinton.store.infrastructure.admin.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, String> {
}
