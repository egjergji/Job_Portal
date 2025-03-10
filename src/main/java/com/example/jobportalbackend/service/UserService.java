package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.DuplicateResourceException;
import com.example.jobportalbackend.exception.AuthenticationException;
import com.example.jobportalbackend.mapper.UserMapper;
import com.example.jobportalbackend.model.dto.LoginRequest;
import com.example.jobportalbackend.model.dto.LoginResponse;
import com.example.jobportalbackend.model.dto.RegisterRequest;
import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import com.example.jobportalbackend.repository.UserRepository;
import com.example.jobportalbackend.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    public Long getJobSeekerIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (user instanceof JobSeeker) {
            return user.getId();
        } else {
            throw new ResourceNotFoundException("User is not a job seeker: " + username);
        }
    }

    public Long getEmployerIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));

        if (user instanceof Employer) {
            return user.getId();
        } else {
            throw new ResourceNotFoundException("User is not an employer: " + username);
        }
    }


    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtUtil.generateToken(user, authentication));
            loginResponse.setUser(userMapper.toDto(user));

            return loginResponse;
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password");
        }
    }


    public UserDTO register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUserDetails().getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already taken");
        }

        UserDTO userDTO = registerRequest.getUserDetails();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser;
        if (userDTO.getRole() == Role.EMPLOYER) {
            newUser = new Employer(
                    userDTO.getUsername(),
                    encodedPassword,
                    Role.EMPLOYER,
                    userDTO.getCompanyName(),
                    userDTO.getCompanyDescription()
            );
        } else if (userDTO.getRole() == Role.JOBSEEKER) {
            newUser = new JobSeeker(
                    userDTO.getUsername(),
                    encodedPassword,
                    Role.JOBSEEKER,
                    userDTO.getPhoneNumber()
            );
        } else {
            newUser = new User(
                    userDTO.getUsername(),
                    encodedPassword,
                    Role.ADMIN
            );
        }

        User savedUser = userRepository.save(newUser);
        return userMapper.toDto(savedUser);
    }


    public Page<UserDTO> getAllUsers(Role role, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        Page<User> users = (role != null)
                ? userRepository.findByRole(role, fixedPageable)
                : userRepository.findAll(fixedPageable);

        return users.map(userMapper::toDto);
    }

@Transactional
    public void deleteUser(@NonNull String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new ResourceNotFoundException("User with username " + username + " not found");
        }
        userRepository.deleteByUsername(username);
    }
}
