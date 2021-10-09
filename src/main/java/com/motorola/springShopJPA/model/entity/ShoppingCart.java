package com.motorola.springShopJPA.model.entity;


import com.motorola.springShopJPA.model.dto.ArticleDto;

import java.math.BigDecimal;
import java.util.Map;

public class ShoppingCart {

    private Long shopUserId;
    private Map<Long, ArticleDto> articles;
    private Long specialOfferId;
    private BigDecimal totalValue;


    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }

    public Map<Long, ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(Map<Long, ArticleDto> articles) {
        this.articles = articles;
    }

    public Long getSpecialOfferId() {
        return specialOfferId;
    }

    public void setSpecialOfferId(Long specialOfferId) {
        this.specialOfferId = specialOfferId;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public ShoppingCart() {
    }
}

