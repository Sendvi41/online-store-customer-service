package com.epam.druzhinin.services;

import com.epam.druzhinin.document.ProductDocument;
import com.epam.druzhinin.dto.search.SearchProductRequestDto;
import com.epam.druzhinin.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.Operator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final ElasticsearchRestTemplate client;

    public ProductService(ProductRepository productRepository,
                          ElasticsearchRestTemplate client) {
        this.productRepository = productRepository;
        this.client = client;
    }

    public void saveProducts(List<ProductDocument> productDocuments) {
        productRepository.saveAll(productDocuments);
    }

    public ProductDocument saveProduct(ProductDocument productDocument) {
        log.info("Starting to save the productDocument [id={}]", productDocument.getId());
        ProductDocument savedProduct = productRepository.save(productDocument);
        log.info("ProductDocument is saved [id={}]", savedProduct.getId());
        return savedProduct;
    }

    public List<ProductDocument> searchProducts(SearchProductRequestDto request, Pageable pageable) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (request.getCategory() != null) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withQuery(matchQuery("category", request.getCategory()));
        }
        if (request.getName() != null) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withQuery(matchQuery("name", request.getName())
                    .operator(Operator.AND));
        }
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder
                .withPageable(pageable)
                .build();
        SearchHits<ProductDocument> productsHits = client.search(searchQuery, ProductDocument.class, IndexCoordinates.of("products"));
        return productsHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }
}
