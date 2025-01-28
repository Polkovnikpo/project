package com.example.simbirsoft.security.service;

import com.example.simbirsoft.security.entity.User;
import org.springframework.jdbc.core.SqlReturnType;

import java.util.List;

public interface UserService {
    User register(User user);

    User findByUsername(String username);
}
