package com.example.dgap.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User register(String userId, String password) {

        userId = normalizeUserId(userId);

        if (userRepository.findByUserId(userId).isPresent()) {
            return null;
        }

        User user = new User();

        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public boolean login(String userId, String password) {

        if (userId == null || password == null) {
            return false;
        }

        Optional<User> user =
                userRepository.findByUserId(normalizeUserId(userId));

        return user.isPresent()
                && passwordEncoder.matches(
                    password,
                    user.get().getPassword()
                );
    }

    public Optional<User> findByUserId(String userId) {
        return userId == null
                ? Optional.empty()
                : userRepository.findByUserId(normalizeUserId(userId));
    }

    public static String normalizeUserId(String userId) {
        return userId == null ? "" : userId.trim().toLowerCase();
    }
}
