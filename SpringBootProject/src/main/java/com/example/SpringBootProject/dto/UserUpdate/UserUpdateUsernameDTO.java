package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateUsernameDTO(
        Long id,
        String username
) implements Serializable {
}
