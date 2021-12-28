package com.epam.druzhinin.listener;

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

    public ProductListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(
            queues = "${rabbitmq.queue}",
            autoStartup = "true")
    public void receiveProducts(ProductQueueDto productQueueDto) {
        log.info("Product was received [id={}, queueTitle={}]", productQueueDto.getProductEntity().getId(), productQueueDto.getQueueTitle());
        QueueTitle queueTitle = productQueueDto.getQueueTitle();
        if (queueTitle.equals(QueueTitle.CREATE)) {
            productService.saveProduct(productQueueDto.getProductEntity());
        } else if (queueTitle.equals(QueueTitle.UPDATE)) {
            productService.updateProduct(productQueueDto.getProductEntity());
        } else if (queueTitle.equals(QueueTitle.DELETE)) {
            productService.deleteProduct(productQueueDto.getProductEntity().getId());
        }
    }
}
