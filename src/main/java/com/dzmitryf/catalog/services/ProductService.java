package com.dzmitryf.catalog.services;

import com.dzmitryf.catalog.model.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> getProducts();

    List<Product> getProductsQuery();
}
