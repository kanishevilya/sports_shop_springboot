package com.example.SpringBootProject.dto.Authentication;

import java.io.Serializable;

public record JwtTokenDTO(
        String username,
        String token
) implements Serializable {
}