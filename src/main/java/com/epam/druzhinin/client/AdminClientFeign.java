package com.epam.druzhinin.client;


import com.epam.druzhinin.document.ProductDocument;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "adminClient", url = "${feign.client.config.adminClient.url}")
@ConditionalOnProperty(value = "feign.client.config.adminClient.isStub", havingValue = "false")
public interface AdminClientFeign extends AdminClient {
    @GetMapping("/products/{id}")
    ProductDocument getProductById(@PathVariable Long id);
}
