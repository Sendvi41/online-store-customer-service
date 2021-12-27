package com.epam.druzhinin;

import com.epam.druzhinin.listener.ProductListener;
import com.epam.druzhinin.repositories.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@TestConfiguration
public class TestContextConfiguration {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @MockBean
    private ProductListener productListener;
}
