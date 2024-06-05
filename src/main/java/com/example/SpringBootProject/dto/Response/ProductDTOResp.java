package com.example.SpringBootProject.dto.Response;

import java.io.Serializable;

public record ProductDTOResp(
        Long id,
        String title,
        String brand,
        Double price,
        String size,
        String color
) implements Serializable {
}
