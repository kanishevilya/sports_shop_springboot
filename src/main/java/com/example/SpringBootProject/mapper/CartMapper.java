package com.example.SpringBootProject.mapper;

import com.example.SpringBootProject.dto.CartDTO;
import com.example.SpringBootProject.dto.CartDetailDTO;
import com.example.SpringBootProject.model.Cart;
import com.example.SpringBootProject.model.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartDTO cartToCartDTO(Cart cart);

    @Mapping(target = "user", ignore = true)
    Cart cartDTOToCart(CartDTO cartDTO);

    List<CartDTO> cartListToCartDTOList(List<Cart> cartList);
    List<Cart> cartDTOListToCartList(List<CartDTO> cartDTOList);

    CartDetailDTO cartDetailToCartDetailDTO(CartDetail cartDetail);
}
