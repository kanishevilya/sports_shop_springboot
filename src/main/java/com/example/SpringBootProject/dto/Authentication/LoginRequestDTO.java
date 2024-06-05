package com.example.SpringBootProject.dto.Authentication;


import java.io.Serializable;

public record LoginRequestDTO(
        String username,
        String password
) implements Serializable {
}
