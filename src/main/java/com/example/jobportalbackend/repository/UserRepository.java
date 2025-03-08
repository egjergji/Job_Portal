package com.example.jobportalbackend.repository;

import com.example.jobportalbackend.model.entity.User;
import com.example.jobportalbackend.model.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Page<User> findByRole(Role role, Pageable pageable);

    Optional<User> findByUsername(String username);

    boolean existsById(Long id);

    void deleteById(Long id);


}
