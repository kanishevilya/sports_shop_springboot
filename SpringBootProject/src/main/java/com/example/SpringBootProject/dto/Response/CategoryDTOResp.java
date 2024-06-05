package com.example.SpringBootProject.dto.Response;


import java.io.Serializable;
import java.util.List;

public record CategoryDTOResp (
        Long id,
        String name
) implements Serializable {
}
