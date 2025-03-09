package com.example.jobportalbackend.service;

import com.example.jobportalbackend.exception.ResourceNotFoundException;
import com.example.jobportalbackend.exception.DuplicateResourceException;
import com.example.jobportalbackend.exception.AuthenticationException;
import com.example.jobportalbackend.mapper.UserMapper;
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

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper; // ✅ Inject UserMapper

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


    public String authenticate(UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            User user = userRepository.findByUsername(userDTO.getUsername())
                    .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

            return jwtUtil.generateToken(user.getUsername());
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password");
        }
    }


    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already taken");
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
        return userMapper.toDto(savedUser);
    }

    // ✅ Get All Users (Uses UserMapper)
    public Page<UserDTO> getAllUsers(Role role, int page) {
        Pageable fixedPageable = PageRequest.of(page, 10);
        Page<User> users = (role != null)
                ? userRepository.findByRole(role, fixedPageable)
                : userRepository.findAll(fixedPageable);

        return users.map(userMapper::toDto);
    }


    public void deleteUser(@NonNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
