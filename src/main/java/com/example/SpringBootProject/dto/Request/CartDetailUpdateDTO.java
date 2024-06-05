package com.example.SpringBootProject.dto.Request;

import java.io.Serializable;

public record CartDetailUpdateDTO(
        Long cartDetailId,
        int amount
) implements Serializable {
}
