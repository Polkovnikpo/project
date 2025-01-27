package com.example.simbirsoft.security.repository;

import com.example.simbirsoft.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
