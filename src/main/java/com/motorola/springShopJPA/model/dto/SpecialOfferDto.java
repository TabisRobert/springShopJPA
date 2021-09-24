package com.motorola.springShopJPA.model.dto;
import java.util.Set;

public class SpecialOfferDto {
    private Long id;
    private String name;
    private String description;
    private Set<PurchaseOrderDto> purchaseOrderSet;

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
