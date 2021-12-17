package com.epam.druzhinin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddItemDto {
    private Long productId;
    private Integer amount;
}
