package com.example.simbirsoft.security.repository;

import com.example.simbirsoft.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
