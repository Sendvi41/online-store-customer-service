package com.epam.druzhinin.listener;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductListener {

    private final ProductService productService;

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(
            queues = "${rabbitmq.queue}",
            autoStartup = "true")
    public void receiveProducts(ProductDocument productDocument) {
        log.info("Starting to save the productDocument [id={}]", productDocument.getId());
        productService.saveProduct(productDocument);
        log.info("ProductDocument is saved [id={}]", productDocument.getId());
    }

}
