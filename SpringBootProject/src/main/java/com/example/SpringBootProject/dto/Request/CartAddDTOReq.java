package com.example.SpringBootProject.dto.Request;

import java.io.Serializable;

public record CartAddDTOReq(
        Long id_product,
        Integer productsAmount
) implements Serializable {
}
