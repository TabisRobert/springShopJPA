package com.motorola.springShopJPA.model.dto;

import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import com.motorola.springShopJPA.model.enums.UserRole;

import java.util.List;

public class ShopUserDto {
    private Long id;
    private String email;
    private String password;
    private String role;
    private List<PurchaseOrder> purchaseOrderList;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

}
