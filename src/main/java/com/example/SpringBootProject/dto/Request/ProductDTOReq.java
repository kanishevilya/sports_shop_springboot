package com.example.SpringBootProject.dto.Request;

import java.io.Serializable;

public record ProductDTOReq(
        Long id,
        String title,
        String brand,
        Double price,
        Long categoryId,
        String size,
        String color
) implements Serializable {
}
