package com.example.simbirsoft.security.service.impl;

import com.example.simbirsoft.security.entity.Role;
import com.example.simbirsoft.security.entity.Status;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.RoleRepository;
import com.example.simbirsoft.security.repository.UserRepository;
import com.example.simbirsoft.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user){
        Role roleUser = roleRepository.findByName("ROLE_BAYER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registerUser = userRepository.save(user);

        log.info("Пользователь: {} успешно зарегистрирован");

        return registerUser;
    }
}
