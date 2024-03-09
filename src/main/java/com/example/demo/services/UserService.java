package com.example.demo.services;


import com.example.demo.enums.RoleName;
import com.example.demo.models.User;
import com.example.demo.models.UserRole;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRoleRepository userRoleRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPhoneNumber(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().toString()))
                        .collect(Collectors.toList())
        );
    }
    public void createNewUser(User user){
        Optional<UserRole> userRoleOptional = userRoleRepository.findByRoleName(RoleName.USER);
        if (userRoleOptional.isPresent()) {
            UserRole userRole = userRoleOptional.get();
            user.setRoles(Collections.singleton(userRole));
            try {
                userRepository.save(user);
            } catch (Exception e){
                System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
            }
        }
        else {
            UserRole defaultRole = new UserRole();
            defaultRole.setRoleName(RoleName.USER);
            user.setRoles(Collections.singleton(defaultRole));
            try {
                userRepository.save(user);
            }catch (Exception e){
                System.out.println("Ошибка при сохранении пользователя: " + e.getMessage());
            }
        }
    }

}
