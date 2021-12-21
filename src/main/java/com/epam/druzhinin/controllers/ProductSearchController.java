package com.epam.druzhinin.controllers;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.search.SearchProductRequestDto;
import com.epam.druzhinin.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductService productService;

    @Autowired
    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public List<ProductDocument> searchProducts(@RequestBody SearchProductRequestDto request, @PageableDefault(size = 4) Pageable pageable) {
        return productService.searchProducts(request, pageable);
    }
}
