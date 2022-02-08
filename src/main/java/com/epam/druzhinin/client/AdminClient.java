package com.epam.druzhinin.client;

import com.epam.druzhinin.document.ProductDocument;

public interface AdminClient {
    ProductDocument getProductById(Long id);
}
