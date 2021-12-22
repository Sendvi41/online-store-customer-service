package com.epam.druzhinin.controllers;

import com.epam.druzhinin.dto.OrderDto;
import com.epam.druzhinin.services.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto createOrder(@RequestParam(value = "userId") Long userId) {
        return orderService.createOrder(userId);
    }

    @GetMapping
    public List<OrderDto> getOrdersByUserId(
            @RequestParam(value = "userId") Long userId,
            @PageableDefault(size = 4) Pageable pageable
    ) {
        return orderService.getOrders(userId, pageable);
    }
}
