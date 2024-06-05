package com.example.SpringBootProject.mapper;

import com.example.SpringBootProject.dto.Authentication.RegisterRequestDTO;
import com.example.SpringBootProject.dto.UserDTO;
import com.example.SpringBootProject.model.Role;
import com.example.SpringBootProject.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    List<UserDTO> usersToUsersDTO(List<User> users);
}