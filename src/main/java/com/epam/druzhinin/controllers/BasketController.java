package com.epam.druzhinin.controllers;

import com.epam.druzhinin.dto.BasketItemsDto;
import com.epam.druzhinin.services.BasketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/{userId}")
    public BasketItemsDto getItemsByUserId(@PathVariable Long userId) {
        return basketService.getItemsByUserId(userId);
    }
}
