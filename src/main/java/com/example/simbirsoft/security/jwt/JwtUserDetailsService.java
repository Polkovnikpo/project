package com.example.simbirsoft.security.jwt;

import com.example.simbirsoft.security.entity.User;
import com.example.simbirsoft.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(JwtUserDetailsService.class);
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Пользователя с username: " + username + " не существует");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("В loadByUsername - пользователь с username: {} успешно загружен", username);

        return jwtUser;
    }
}
