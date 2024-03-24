package com.example.demo.services;

import com.example.demo.dtos.RegistrationUserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    public AuthService(UserService userService, JwtTokenUtils jwtTokenUtils, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticateAndGetToken(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserDetails userDetails = userService.loadUserByUsername(username);
            return jwtTokenUtils.generateToken(userDetails);
        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, "Ошибка аутентификации: " + e.getMessage());
            throw new AuthenticationServiceException("Неверный логин или пароль", e);
        }
    }

    public void createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setRole(1);
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setPhoneNumber(registrationUserDto.getPhoneNumber());
        userRepository.createNewUser(user);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
        }
    }
}

