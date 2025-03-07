package com.example.jobportalbackend.service;

import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import com.example.jobportalbackend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDTO> getAllUsers(Role role, int page, int size) {
        Page<User> users = userRepository.findByRole(role, PageRequest.of(page, size));
        return users.map(user -> new UserDTO(user.getId(), user.getUsername(), user.getRole()));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
