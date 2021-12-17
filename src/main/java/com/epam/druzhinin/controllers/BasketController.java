package com.epam.druzhinin.controllers;

import com.epam.druzhinin.dto.AddItemDto;
import com.epam.druzhinin.dto.BasketItemsDto;
import com.epam.druzhinin.dto.ItemDto;
import com.epam.druzhinin.services.BasketService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{userId}")
    public ItemDto addItemToBasket(@PathVariable Long userId, @RequestBody AddItemDto addItemDto) {
        return basketService.addItem(addItemDto, userId);
    }
}
