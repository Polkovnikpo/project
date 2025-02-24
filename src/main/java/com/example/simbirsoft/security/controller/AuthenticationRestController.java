package com.example.simbirsoft.security.controller;

import com.example.simbirsoft.security.dto.AuthenticationRequestDto;
import com.example.simbirsoft.security.entity.Role;
import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.jwt.JwtTokenProvider;
import com.example.simbirsoft.security.service.UserService;
import com.example.simbirsoft.service.AirlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationRestController {
    private final static Logger log = LoggerFactory.getLogger(AirlineService.class);
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        JwtTokenProvider jwtTokenProvider,
                                        UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequestDto requestDto) {
        log.info("Получен запрос на логин: {}", requestDto);
        try {
            String username = requestDto.getUsername();
            log.info("Аутентификация пользователя: {}", username);

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            User user = userService.findByUsername(username);
            if (user == null) {
                log.error("Пользователь не найден: {}", username);
                throw new UsernameNotFoundException("Пользователь не найден");
            }

            String token = jwtTokenProvider.createToken(username, new ArrayList<>(user.getRoles()));
            log.info("Токен создан для пользователя: {}", username);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Ошибка аутентификации: {}", e.getMessage());
            throw new BadCredentialsException("Неверное имя пользователя или пароль");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            User user = new User();
            user.setUsername(requestDto.getUsername());
            user.setPassword(requestDto.getPassword());

            User registeredUser = userService.register(user, "ROLE_BUYER");

            return ResponseEntity.ok("Пользователь " +
                    registeredUser.getUsername() + " зарегестрирован с ролью " + "ROLE_BUYER");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка регистрации: " + e.getMessage());
        }
    }
}
