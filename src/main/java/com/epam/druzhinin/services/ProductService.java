package com.epam.druzhinin.services;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void saveProducts(List<ProductDocument> productDocuments) {
        productRepository.saveAll(productDocuments);
    }

    public ProductDocument saveProduct(ProductDocument productDocument) {
        log.info("Starting to save the productDocument [id={}]", productDocument.getId());
        ProductDocument savedProduct = productRepository.save(productDocument);
        log.info("ProductDocument is saved [id={}]", savedProduct.getId());
        return savedProduct;
    }
}
