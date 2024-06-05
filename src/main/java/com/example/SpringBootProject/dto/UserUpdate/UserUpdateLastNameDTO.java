package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateLastNameDTO(
        Long id,
        String lastName
) implements Serializable {
}