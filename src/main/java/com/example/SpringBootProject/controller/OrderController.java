package com.example.SpringBootProject.controller;

import com.example.SpringBootProject.dto.CartDTO;
import com.example.SpringBootProject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;

    @GetMapping("/makeOrder")
    @ResponseStatus(HttpStatus.OK)
    public CartDTO makeOrder(@RequestParam Long userId) {
        return cartService.makePurchase(userId);
    }
}
