package com.epam.druzhinin;

import com.epam.druzhinin.listener.ProductListener;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestContextConfiguration {

    @MockBean
    private ProductListener productListener;
}
