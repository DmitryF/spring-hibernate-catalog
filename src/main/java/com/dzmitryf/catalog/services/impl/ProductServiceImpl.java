package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.dao.ProductDao;
import com.dzmitryf.catalog.model.Product;
import com.dzmitryf.catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private EntityManager entityManager;

    public Product save(Product product) {
        return productDao.save(product);
    }

    public List<Product> getProducts() {

        Query query = entityManager.createNativeQuery("SELECT * FROM hbschema.product", Product.class);
        List<Product> products = (List<Product>) query.getResultList();

        return products;
    }

    public List<Product> getProductsQuery() {
        return productDao.getProductsQuery();
    }
}
