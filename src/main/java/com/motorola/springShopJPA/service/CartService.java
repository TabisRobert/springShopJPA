package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.entity.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final ShopUserService shopUserService;

    public CartService(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }


    public ShoppingCart createNewCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShopUserId(shopUserService.findCurrentlyLoggedUser().getId());
        return shoppingCart;
    }
}
