package com.motorola.springShopJPA.model.entity;



import com.motorola.springShopJPA.model.enums.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;


@Entity
public class ShopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @Size(max = 100)
    private String password;

    @NotNull
    private String role;

    @OneToMany(mappedBy = "shopUser")
    private Map<Long, PurchaseOrder> purchaseOrderList;

    private boolean enabled;

    public ShopUser() {
    }

    public ShopUser(Long id, @NotNull @Size(max = 50) String email, @NotNull @Size(max = 50) String password, @NotNull String role, Map<Long, PurchaseOrder> purchaseOrderList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.purchaseOrderList = purchaseOrderList;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Map<Long, PurchaseOrder> getPurchaseOrderList() {
        return purchaseOrderList;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPurchaseOrderList(Map<Long, PurchaseOrder> purchaseOrderList) {
        this.purchaseOrderList = purchaseOrderList;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
