package com.motorola.springShopJPA.model.dto;

import com.motorola.springShopJPA.model.entity.Product;

import java.math.BigDecimal;

public class ArticleDto {

    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal articleTotalPrice;
    private boolean isOrdered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getArticleTotalPrice() {
        return articleTotalPrice;
    }

    public void setArticleTotalPrice(BigDecimal articleTotalPrice) {
        this.articleTotalPrice = articleTotalPrice;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }
}
