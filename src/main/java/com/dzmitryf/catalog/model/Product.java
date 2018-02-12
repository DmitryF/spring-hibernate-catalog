package com.dzmitryf.catalog.model;

import javax.persistence.*;

@Entity
@EntityListeners(ProductListener.class)
@Table(name = "hbschema.product", catalog="hbschema")
public class Product {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private int id;

    @Column(name = "NAME", unique = false, nullable = false)
    private String name;

    @Column(name = "PRICE", unique = false, nullable = false)
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}