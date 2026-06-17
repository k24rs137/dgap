package com.example.dgap.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dgap.model.User;
import com.example.dgap.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User register(String userId, String password) {

        if (userRepository.findByUserId(userId).isPresent()) {
            return null;
        }

        User user = new User();

        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public boolean login(String userId, String password) {

        Optional<User> user =
                userRepository.findByUserId(userId);

        return user.isPresent()
                && passwordEncoder.matches(
                    password,
                    user.get().getPassword()
                );
    }
}