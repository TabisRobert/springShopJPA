package com.motorola.springShopJPA.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
