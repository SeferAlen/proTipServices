package com.protip.proTipServices.repository;

import com.protip.proTipServices.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    public abstract Role findByName(final String name);
}
