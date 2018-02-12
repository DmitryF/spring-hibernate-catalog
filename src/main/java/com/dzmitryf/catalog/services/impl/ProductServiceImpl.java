package com.dzmitryf.catalog.services.impl;

import com.dzmitryf.catalog.dao.ProductDao;
import com.dzmitryf.catalog.model.Product;
import com.dzmitryf.catalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Product save(Product product) {

        entityManager.persist(product);

        return product;
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
