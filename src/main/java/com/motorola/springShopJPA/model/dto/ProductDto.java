package com.motorola.springShopJPA.model.dto;

import com.motorola.springShopJPA.model.entity.Article;

import java.math.BigDecimal;
import java.util.Set;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private ProductCategoryDto productCategory;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategoryDto getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategoryDto productCategory) {
        this.productCategory = productCategory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, BigDecimal price, ProductCategoryDto productCategory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCategory = productCategory;
    }
}
