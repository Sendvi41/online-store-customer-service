package com.epam.druzhinin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class BasketItemsDto {
    private Long basketId;
    private Long userId;
    private List<ItemDto> items;
}
