package com.epam.druzhinin;

import com.epam.druzhinin.repositories.UserRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class TestContextConfiguration {
    @MockBean
    private UserRepository userRepository;
}
