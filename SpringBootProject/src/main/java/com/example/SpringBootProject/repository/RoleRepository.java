package com.example.SpringBootProject.repository;

import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
