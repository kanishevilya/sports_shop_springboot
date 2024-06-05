package com.example.SpringBootProject.dto.UserUpdate;

import java.io.Serializable;

public record UserUpdateCityDTO(
        Long id,
        String city
) implements Serializable {
}