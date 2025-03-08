package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import com.example.jobportalbackend.repository.UserRepository;
import com.example.jobportalbackend.security.JwtUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
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

    public String authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );

        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        return jwtUtil.generateToken(user.getUsername());
    }

    public UserDTO register(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User newUser;

        if (userDTO.getRole() == Role.EMPLOYER) {
            newUser = new Employer(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    Role.EMPLOYER,
                    userDTO.getCompanyName(),
                    userDTO.getCompanyDescription()
            );
        } else if (userDTO.getRole() == Role.JOBSEEKER) {
            newUser = new JobSeeker(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    Role.JOBSEEKER,
                    userDTO.getResumeLink(),
                    userDTO.getPhoneNumber()
            );
        } else {
            newUser = new User(
                    userDTO.getUsername(),
                    passwordEncoder.encode(userDTO.getPassword()),
                    Role.ADMIN
            );
        }

        User savedUser = userRepository.save(newUser);

        return new UserDTO(savedUser.getId(), savedUser.getPassword(), savedUser.getUsername(), savedUser.getRole(),
                (savedUser instanceof Employer) ? ((Employer) savedUser).getCompanyName() : null,
                (savedUser instanceof Employer) ? ((Employer) savedUser).getCompanyDescription() : null,
                (savedUser instanceof JobSeeker) ? ((JobSeeker) savedUser).getResumeLink() : null,
                (savedUser instanceof JobSeeker) ? ((JobSeeker) savedUser).getPhoneNumber() : null);
    }

    public Page<UserDTO> getAllUsers(Role role, int page, int size) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        Page<User> users;

        if (role != null) {
            users = userRepository.findByRole(role, fixedPageable);
        } else {
            users = userRepository.findAll(fixedPageable);
        }

        return users.map(user -> new UserDTO(user.getId(), user.getUsername(), user.getRole()));
    }

    public void deleteUser(@NonNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

}



