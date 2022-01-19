package com.epam.druzhinin.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.epam.druzhinin.repositories")
@ComponentScan(basePackages = {"com.epam.druzhinin"})
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("${elastic.host}")
    private String elasticHost;

    @Value("${elastic.port}")
    private String elasticPort;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticHost, Integer.parseInt(elasticPort), "https")));
        return client;
    }
}
