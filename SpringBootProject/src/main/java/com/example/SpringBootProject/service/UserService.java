package com.example.SpringBootProject.service;

import com.example.SpringBootProject.dto.UserDTO;
import com.example.SpringBootProject.mapper.UserMapper;
import com.example.SpringBootProject.model.User;
import com.example.SpringBootProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public Page<User> getUserByPage(Integer pageNumber, Integer pageSize, String columnName, String order){
        Sort sorting=Sort.by(Sort.Direction.fromString(order), columnName);
        PageRequest pageRequest=PageRequest.of(pageNumber, pageSize, sorting);
        return userRepository.findAll(pageRequest);
    }

    public List<UserDTO> findAll() {
        return UserMapper.INSTANCE.usersToUsersDTO(userRepository.findAll());
    }

    public UserDTO findById(Long id) {
        return UserMapper.INSTANCE.userToUserDTO(userRepository.findById(id).orElseThrow());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


}
