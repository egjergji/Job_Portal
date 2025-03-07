package com.example.jobportalbackend.controller;

import com.example.jobportalbackend.model.dto.EmployerDTO;
import com.example.jobportalbackend.service.EmployerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    private final EmployerService employerService;

    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/{id}")
    public EmployerDTO getEmployerById(@PathVariable Long id) {
        return employerService.getEmployerById(id);
    }
}
