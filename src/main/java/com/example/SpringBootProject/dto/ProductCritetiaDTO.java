package com.example.SpringBootProject.dto;

import java.io.Serializable;

public record ProductCritetiaDTO(
        String title,
        String brand,
        Double price,
        String category,
        String size,
        String color
) implements Serializable {
}