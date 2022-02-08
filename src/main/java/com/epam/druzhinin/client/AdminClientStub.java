package com.epam.druzhinin.client;

import com.epam.druzhinin.document.ProductDocument;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Component
@ConditionalOnProperty(value = "feign.client.config.adminClient.isStub", havingValue = "true")
public class AdminClientStub implements AdminClient {
    @Override
    public ProductDocument getProductById(Long id) {
        return new ProductDocument()
                .setId(id.toString())
                .setPrice(BigDecimal.valueOf(1000))
                .setDate(ZonedDateTime.now())
                .setAmount(5)
                .setName("table")
                .setCategory("furniture");
    }
}
