package com.cybersoft.beatstreet.repository;

import com.cybersoft.beatstreet.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByRole(String role);
}
