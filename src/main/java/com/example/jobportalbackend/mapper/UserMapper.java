package com.example.jobportalbackend.mapper;

import com.example.jobportalbackend.model.dto.UserDTO;
import com.example.jobportalbackend.model.entity.Employer;
import com.example.jobportalbackend.model.entity.JobSeeker;
import com.example.jobportalbackend.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setRole(userDTO.getRole());
        return user;
    }

    @Override
    public UserDTO toDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(
                user.getUsername(),
                user.getRole(),
                (user instanceof Employer) ? ((Employer) user).getCompanyName() : null,
                (user instanceof Employer) ? ((Employer) user).getCompanyDescription() : null,
                (user instanceof JobSeeker) ? ((JobSeeker) user).getPhoneNumber() : null
        );
    }
}
