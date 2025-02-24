package com.example.simbirsoft.security.service;

import com.example.simbirsoft.security.entity.Role;
import com.example.simbirsoft.security.entity.Status;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.repository.RoleRepository;
import com.example.simbirsoft.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService{

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User register(User user, String roleName){
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Роль " + roleName + " не найдена");
        }

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        logger.info("Пароль перед сохранением (хешированный): {}", encodedPassword);

        user.setPassword(encodedPassword);
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        logger.info("Пользователь {} зарегистрирован с ролью: {}", user.getUsername(), roleName);

        return registeredUser;
    }
    
    public User findByUsername(String username){
        User result = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        logger.info("В методе findUsername - пользователь: {} найден по имени пользователя: {}",result,username);
        return result;
    }

}
