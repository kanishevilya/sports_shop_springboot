package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateCountryDTO(
        Long id,
        String country
) implements Serializable {
}