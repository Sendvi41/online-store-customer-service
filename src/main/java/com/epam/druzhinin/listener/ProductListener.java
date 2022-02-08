package com.epam.druzhinin.listener;

import com.epam.druzhinin.client.AdminClient;
import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.ProductQueueDto;
import com.epam.druzhinin.enums.QueueTitle;
import com.epam.druzhinin.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductListener {

    private final ProductService productService;

    private final AdminClient adminClient;

    public ProductListener(ProductService productService,
                           AdminClient adminClient) {
        this.productService = productService;
        this.adminClient = adminClient;
    }

    @RabbitListener(
            queues = "${rabbitmq.queue}",
            autoStartup = "true")
    public void receiveProducts(ProductQueueDto productQueueDto) {
        log.info("Product was received [id={}, queueTitle={}]", productQueueDto.getProductId(), productQueueDto.getQueueTitle());
        QueueTitle queueTitle = productQueueDto.getQueueTitle();
        if (queueTitle.equals(QueueTitle.CREATE)) {
            ProductDocument productDocument = adminClient.getProductById(productQueueDto.getProductId());
            productService.saveProduct(productDocument);
        } else if (queueTitle.equals(QueueTitle.UPDATE)) {
            ProductDocument productDocument = adminClient.getProductById(productQueueDto.getProductId());
            productService.updateProduct(productDocument);
        } else if (queueTitle.equals(QueueTitle.DELETE)) {
            productService.deleteProduct(productQueueDto.getProductId().toString());
        }
    }
}
