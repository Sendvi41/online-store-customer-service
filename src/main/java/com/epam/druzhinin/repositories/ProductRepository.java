package com.epam.druzhinin.repositories;

import com.epam.druzhinin.document.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {
}
