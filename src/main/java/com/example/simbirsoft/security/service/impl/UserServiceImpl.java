package com.example.simbirsoft.security.service.impl;

import com.example.simbirsoft.security.entity.Role;
import com.example.simbirsoft.security.entity.Status;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.RoleRepository;
import com.example.simbirsoft.security.repository.UserRepository;
import com.example.simbirsoft.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user, String roleName){
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Роль " + roleName + " не найдена");
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        logger.info("Пользователь {} зарегистрирован с ролью: {}", user.getUsername(), roleName);

        return registeredUser;
    }

    @Override
    public User findByUsername(String username){
        User result = userRepository.findByUsername(username);
        logger.info("В методе findUsername - пользователь: {} найден по имени пользователя: {}",result,username);
        return result;
    }

}
