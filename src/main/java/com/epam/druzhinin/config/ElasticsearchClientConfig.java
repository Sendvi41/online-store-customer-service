package com.epam.druzhinin.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.epam.druzhinin.repositories")
@ComponentScan(basePackages = {"com.epam.druzhinin"})
public class ElasticsearchClientConfig extends AbstractElasticsearchConfiguration {
    @Value("${elastic.host}")
    private String elasticHost;

    @Value("${elastic.port}")
    private String elasticPort;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration =
                ClientConfiguration
                        .builder()
                        .connectedTo(elasticHost + ":" + elasticPort)
                        .build();

        return RestClients.create(clientConfiguration).rest();
    }
}