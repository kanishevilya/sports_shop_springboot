package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateEmailDTO(
        Long id,
        String email
) implements Serializable {
}
