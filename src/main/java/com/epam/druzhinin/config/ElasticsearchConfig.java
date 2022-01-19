package com.epam.druzhinin.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@Primary
public class ElasticsearchConfig {

    @Value("${elastic.host}")
    private String elasticHost;

    @Value("${elastic.port}")
    private String elasticPort;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient client() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(elasticHost, Integer.parseInt(elasticPort), "https")));
        return client;
    }
}
