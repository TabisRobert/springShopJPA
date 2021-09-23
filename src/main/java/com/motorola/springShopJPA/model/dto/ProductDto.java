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
    private Set<Article> articles;

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

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
