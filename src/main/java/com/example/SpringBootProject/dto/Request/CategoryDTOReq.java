package com.example.SpringBootProject.dto.Request;

import java.io.Serializable;
import java.util.List;

public record CategoryDTOReq (
    Long id,
    String name,
    List<ProductDTOReq> products
) implements Serializable {
}
