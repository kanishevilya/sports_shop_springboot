package com.example.SpringBootProject.dto;

import java.io.Serializable;
import java.util.List;

public record CartDTO(
        UserDTO user,
        List<CartDetailDTO> details
) implements Serializable {
}
