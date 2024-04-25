package org.enset.hospital.repository;

import org.enset.hospital.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
    AppRole findByRole(String role);
}
