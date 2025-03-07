package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import com.example.jobportalbackend.repository.UserRepository;
import com.example.jobportalbackend.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // ✅ Register a new user
    public UserDTO register(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User newUser = new User(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),  // ✅ Encrypt password
                userDTO.getRole()
        );

        User savedUser = userRepository.save(newUser);

        return new UserDTO(savedUser.getId(), savedUser.getUsername(), savedUser.getRole());
    }

    public String authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        return jwtUtil.generateToken(user.getUsername());
    }

    public Page<UserDTO> getAllUsers(Role role, int page, int size) {
        Page<User> users = userRepository.findByRole(role, PageRequest.of(page, size));
        return users.map(user -> new UserDTO(user.getId(), user.getUsername(), user.getRole()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
