package com.example.demo.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationUserDto {
    @NotNull(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotNull(message = "Пароль не может быть пустым")
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;

    @NotNull(message = "Подтверждение пароля не может быть пустым")
    @NotEmpty(message = "Подтверждение пароля не может быть пустым")
    private String confirmPassword;

    private String phoneNumber;
}
