package com.motorola.springShopJPA.model.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PurchaseOrderDto {

    private Long id;
    private Map<Long, ArticleDto> articles;
    private ShopUserDto shopUser;
    private SpecialOfferDto specialOffer;
    private BigDecimal totalValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, ArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(Map<Long, ArticleDto> articles) {
        this.articles = articles;
    }

    public ShopUserDto getShopUser() {
        return shopUser;
    }

    public void setShopUser(ShopUserDto shopUser) {
        this.shopUser = shopUser;
    }

    public SpecialOfferDto getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOfferDto specialOffer) {
        this.specialOffer = specialOffer;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
