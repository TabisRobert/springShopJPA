package com.motorola.springShopJPA.model.dto;

import com.motorola.springShopJPA.model.entity.Product;

import java.util.Set;

public class ProductCategoryDto {

    private Long id;
    private String name;
    private Set<Product> productSet;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
