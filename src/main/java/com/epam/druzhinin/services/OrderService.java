package com.epam.druzhinin.services;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.OrderDto;
import com.epam.druzhinin.dto.OrderedProductDto;
import com.epam.druzhinin.entity.*;
import com.epam.druzhinin.enums.OrderStatus;
import com.epam.druzhinin.exception.NotFoundException;
import com.epam.druzhinin.repositories.OrderRepository;
import com.epam.druzhinin.repositories.OrderedProductRepository;
import com.epam.druzhinin.repositories.ProductRepository;
import com.epam.druzhinin.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {
    @Value("${date.time.zone}")
    private String zoneId;

    private final OrderRepository orderRepository;

    private final OrderedProductRepository orderedProductRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderedProductRepository orderedProductRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderedProductRepository = orderedProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public OrderDto createOrder(Long userId) {
        log.info("Starting to create order for user[userId={}]", userId);
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User isn't found with id=" + userId)
        );
        BasketEntity basket = user.getBasket();
        if (basket.getItems().isEmpty()) {
            throw new NotFoundException("Basket doesn't contain items for order");
        }
        OrderEntity orderEntity = new OrderEntity().setDate(ZonedDateTime.now(ZoneId.of(zoneId)))
                .setStatus(OrderStatus.PENDING)
                .setUser(user);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        log.info("Order was created successfully [orderId={}]", orderEntity.getId());
        List<OrderedProductEntity> orderedProductEntities = new ArrayList<>();
        for (ItemEntity item : basket.getItems()) {
            ProductDocument productDocument = productRepository.findById(item.getProductId().toString()).orElseThrow(
                    () -> new NotFoundException("Product isn't found with id=" + item.getProductId())
            );
            OrderedProductEntity orderedProductEntity = new OrderedProductEntity()
                    .setProductId(item.getProductId())
                    .setAmount(item.getAmount())
                    .setOrder(savedOrderEntity)
                    .setPrice(productDocument.getPrice());
            orderedProductRepository.save(orderedProductEntity);
            orderedProductEntities.add(orderedProductEntity);
        }
        OrderDto orderDto = modelMapper.map(savedOrderEntity, OrderDto.class);
        List<OrderedProductDto> orderedProductDtos = orderedProductEntities
                .stream()
                .map(e -> modelMapper.map(e, OrderedProductDto.class))
                .collect(Collectors.toList());
        log.info("OrderEntity was mapped to OrderDto successfully [orderId={}]", savedOrderEntity.getId());
        orderDto.setOrderedProducts(orderedProductDtos);
        return orderDto;
    }
}
