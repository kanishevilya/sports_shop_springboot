package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdatePasswordDTO(
        Long id,
        String password
) implements Serializable {
}
