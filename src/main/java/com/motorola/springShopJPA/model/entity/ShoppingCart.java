package com.motorola.springShopJPA.model.entity;


import com.motorola.springShopJPA.model.dto.ArticleDto;

import java.util.Map;

public class ShoppingCart {

    private Long shopUserId;
    private Map<Long, ArticleDto> articles;

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
}
