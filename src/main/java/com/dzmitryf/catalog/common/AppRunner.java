package com.dzmitryf.catalog.common;

import com.dzmitryf.catalog.config.PersistenceConfig;
import com.dzmitryf.catalog.model.Product;
import com.dzmitryf.catalog.services.ProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class AppRunner {

    public static void main(String[] args){


        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);

        ProductService dataSource = context.getBean(ProductService.class);
        Product product1 = new Product();
        product1.setName("qweqwewqe");
        dataSource.save(product1);

       /* List<Product> products = dataSource.getProducts();
        for (Product product : products) {
            System.out.print(product.getName());
        }*/

        List<Product> productsQuery = dataSource.getProductsQuery();
        for (Product product : productsQuery) {
            System.out.print(product.getName());
        }
    }
}
