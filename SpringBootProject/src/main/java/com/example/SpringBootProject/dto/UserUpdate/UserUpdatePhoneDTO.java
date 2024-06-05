package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdatePhoneDTO(
        Long id,
        String phone
) implements Serializable {
}