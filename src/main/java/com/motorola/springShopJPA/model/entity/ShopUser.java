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
    @Size(max = 50)
    private String password;

    @NotNull
    private UserRole role;

    @OneToMany(mappedBy = "shopUser")
    private Map<Long, PurchaseOrder> purchaseOrderList;

    public ShopUser() {
    }

    public ShopUser(Long id, @NotNull @Size(max = 50) String email, @NotNull @Size(max = 50) String password, @NotNull UserRole role, Map<Long, PurchaseOrder> purchaseOrderList) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.purchaseOrderList = purchaseOrderList;
    }
}
