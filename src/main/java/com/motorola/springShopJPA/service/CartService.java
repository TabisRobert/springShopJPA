package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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

    public void calculateTotalPriceOfArticlesInCart(ShoppingCart cart) {

        Set<Map.Entry<Long, ArticleDto>> articlesSet = cart.getArticles().entrySet();
        Optional<BigDecimal> totalPrice = articlesSet.stream()
                .map(entry -> entry.getValue().getArticleTotalPrice())
                .reduce(BigDecimal::add);
        if (totalPrice.isPresent()) {
            cart.setTotalValue(totalPrice.get());
        } else {
            cart.setTotalValue(BigDecimal.ZERO);
        }
    }
}
