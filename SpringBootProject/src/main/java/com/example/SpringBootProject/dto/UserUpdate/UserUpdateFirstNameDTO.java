package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateFirstNameDTO(
        Long id,
        String firstName
) implements Serializable {
}