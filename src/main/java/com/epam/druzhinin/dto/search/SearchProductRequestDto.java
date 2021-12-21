package com.epam.druzhinin.dto.search;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SearchProductRequestDto {
    private String name;
    private String category;
}
