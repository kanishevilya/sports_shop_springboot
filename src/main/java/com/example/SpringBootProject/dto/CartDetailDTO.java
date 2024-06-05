package com.example.SpringBootProject.dto;

import com.example.SpringBootProject.dto.Request.ProductDTOReq;

import java.io.Serializable;

public record CartDetailDTO(
        ProductDTOReq product,
        int amount
) implements Serializable {
}
