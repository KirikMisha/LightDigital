package com.example.demo.controllers;

import com.example.demo.dtos.RegistrationUserDto;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
//@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public RegistrationController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationUserDto registrationUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Пользователь с таким именем уже существует");
        }

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пароли не совпадают");
        }

        String encodedPassword = passwordEncoder.encode(registrationUserDto.getPassword());

        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(encodedPassword);

        user.setPhoneNumber(registrationUserDto.getPhoneNumber());

        userService.createNewUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
    }
}
