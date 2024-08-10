package com.example.BookHeaven.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.BookHeaven.Utils.JwtUtil;
import com.example.BookHeaven.model.User;
import com.example.BookHeaven.repository.UserRepository;

@Service
public class UserService {

    // @Autowired
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        try {
            // user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            // Handle duplicate email exception
            throw new RuntimeException("Email already exists!");
        }
    }

    public User updateUser(String id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public User deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.deleteById(id);
        }
        return user;
    }

    public User forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Send email with password reset link
        }
        return user;
    }

    // get logged in user details by JWT TOken
    public User getUserDetails(String token) {
        String email = jwtUtil.extractUsername(token);
        return userRepository.findByEmail(email);
    }

    public Map<String, Object> authenticate(String email, String password) throws AuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            final User user = getUserByEmail(email).withPassword(null);
            final String token = jwtUtil.generateToken(email);
            return Map.of("user", user, "token", token);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
