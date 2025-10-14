package com.AsadBabayev.controller.impl;

import com.AsadBabayev.auth.TokenManager;
import com.AsadBabayev.entities.Role;
import com.AsadBabayev.entities.User;
import com.AsadBabayev.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rest/api/auth")
public class AuthController {

    private final UserRepository userRepository;

    private final TokenManager tokenManager;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, TokenManager tokenManager) {
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return "Username already exists!";
        }
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(user.getRole()==null){
            user.setRole(Role.USER);
        }

        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User dbUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = tokenManager.generateToken(dbUser.getUsername(), dbUser.getRole());
        return Map.of("token", token);
    }
}

