package com.epam.druzhinin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class OrderedProductDto {
    private Long id;
    private Long productId;
    private Integer amount;
    private BigDecimal price;
}
