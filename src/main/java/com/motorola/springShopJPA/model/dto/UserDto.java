package com.motorola.springShopJPA.model.dto;

import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import com.motorola.springShopJPA.model.enums.UserRole;

import java.util.List;

public class UserDto {
    private Long id;
    private String email;
    private String password;
    private UserRole role;
    private List<PurchaseOrder> purchaseOrderList;
}
