package com.example.SpringBootProject.dto;


import com.example.SpringBootProject.model.Order;
import com.example.SpringBootProject.model.Role;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public record UserDTO(
        String username,
        String email,
        String password,
        String contactNumber,
        String country,
        String city,
        String firstName,
        String lastName
//        List<Order> orders,
//        Set<Role> roles
//        CartDTO cart
) implements Serializable {
}
