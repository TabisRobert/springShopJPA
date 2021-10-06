package com.motorola.springShopJPA.model.entity;

import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class SpecialOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "special_offer_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private SpecialOfferDiscountType discountType;

    private Short numberOfFreeProducts;

    private BigDecimal discountPercent;


    @OneToMany(mappedBy = "specialOffer")
    private Set<PurchaseOrder> purchaseOrderSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PurchaseOrder> getPurchaseOrderSet() {
        return purchaseOrderSet;
    }

    public void setPurchaseOrderSet(Set<PurchaseOrder> purchaseOrderSet) {
        this.purchaseOrderSet = purchaseOrderSet;
    }
}
