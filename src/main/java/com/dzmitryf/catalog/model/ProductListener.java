package com.dzmitryf.catalog.model;

import javax.persistence.PrePersist;

public class ProductListener {

    @PrePersist
    public void prepersist(Product product){
        System.out.print("product prepersist");
    }
}
