package com.dzmitryf.catalog.dao;

import com.dzmitryf.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    Product save(Product product);

    @Query(value = "SELECT * FROM hbschema.product", nativeQuery = true)
    List<Product> getProductsQuery();
}
