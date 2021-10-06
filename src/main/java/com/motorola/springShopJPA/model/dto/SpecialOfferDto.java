package com.motorola.springShopJPA.model.dto;
import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;

import java.math.BigDecimal;
import java.util.Set;

public class SpecialOfferDto {
    private Long id;
    private String name;
    private String description;
    private Set<PurchaseOrderDto> purchaseOrderSet;
    private SpecialOfferDiscountType discountType;

    private Short numberOfFreeProducts;

    private BigDecimal discountPercent;

    public SpecialOfferDiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(SpecialOfferDiscountType discountType) {
        this.discountType = discountType;
    }

    public Short getNumberOfFreeProducts() {
        return numberOfFreeProducts;
    }

    public void setNumberOfFreeProducts(Short numberOfFreeProducts) {
        this.numberOfFreeProducts = numberOfFreeProducts;
    }

    public BigDecimal getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(BigDecimal discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<PurchaseOrderDto> getPurchaseOrderSet() {
        return purchaseOrderSet;
    }

    public void setPurchaseOrderSet(Set<PurchaseOrderDto> purchaseOrderSet) {
        this.purchaseOrderSet = purchaseOrderSet;
    }
}
