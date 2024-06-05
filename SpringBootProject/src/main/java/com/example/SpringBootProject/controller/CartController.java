package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.CartDTO;
import com.example.SpringBootProject.dto.CartDetailDTO;
import com.example.SpringBootProject.dto.Request.CartAddDTOReq;
import com.example.SpringBootProject.dto.Request.CartDetailUpdateDTO;
import com.example.SpringBootProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping("/getAll")
    public List<CartDTO> getAllCarts(){
        return cartService.getAllCarts();
    }

    @GetMapping("/getTotalPrice")
    public Double getTotalPrice(@RequestParam Long id_user){
        return cartService.getTotalPrice(id_user);
    }

    @GetMapping("/getCartByUserId")
    public CartDTO getCartByUserId(@RequestParam Long id_user){
        return cartService.getCartByUserId(id_user);
    }

    @PostMapping("/addToCart")
    public CartDTO addToCartForUser(@RequestParam Long id_user, @RequestBody CartAddDTOReq cartAddDTOReq){
        return cartService.addToCartForUser(id_user, cartAddDTOReq);
    }

    @GetMapping("/cartClear")
    public void cartClear(@RequestParam Long id_user){
        cartService.cartClear(id_user);
    }

    @PostMapping("/updateCartDetailAmount")
    public CartDetailDTO updateCartDetailAmount(@RequestBody CartDetailUpdateDTO cartDetailUpdateDTO){
        return cartService.updateCartDetailAmount(cartDetailUpdateDTO);
    }

    @GetMapping("/removeCartDetail")
    public CartDTO removeCartDetailFromCart(@RequestParam Long cartDetailId){
        return cartService.removeCartDetailFromCart(cartDetailId);
    }
}
