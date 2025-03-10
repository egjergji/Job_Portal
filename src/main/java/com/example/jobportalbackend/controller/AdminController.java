package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.enums.Role;
import com.example.jobportalbackend.service.UserService;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDTO> getAllUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(defaultValue = "0") int page) {
        return userService.getAllUsers(role, page).getContent();
    }


    @DeleteMapping("/users/{username}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteUser(@PathVariable @NonNull String username) {
        userService.deleteUser(username);
    }

}
