package com.epam.druzhinin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ItemDto {
    private Long id;
    private Long productId;
    private Integer amount;
}
