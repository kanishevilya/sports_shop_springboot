package com.example.SpringBootProject.dto.Authentication;

import java.io.Serializable;

public record RegisterRequestDTO(
        String username,
        String password,
        String email
) implements Serializable {
}