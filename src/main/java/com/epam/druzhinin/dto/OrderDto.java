package com.epam.druzhinin.dto;

import com.epam.druzhinin.enums.OrderStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class OrderDto {
    private Long id;
    private ZonedDateTime date;
    private OrderStatus status;
    private Long userId;
    private List<OrderedProductDto> orderedProducts;
}
