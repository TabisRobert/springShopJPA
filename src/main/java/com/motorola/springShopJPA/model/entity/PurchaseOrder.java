package com.motorola.springShopJPA.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "purchaseOrder")
    private Map<Long, Article> articles;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private ShopUser shopUser;

    @ManyToOne
    @JoinColumn(name = "special_offer_id")
    private SpecialOffer specialOffer;

    private BigDecimal totalValue;

    public PurchaseOrder() {
    }

    public PurchaseOrder(Long id, ShopUser shopUser) {
        this.id = id;
        this.shopUser = shopUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Long, Article> articles) {
        this.articles = articles;
    }

    public ShopUser getShopUser() {
        return shopUser;
    }

    public void setShopUser(ShopUser shopUser) {
        this.shopUser = shopUser;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
