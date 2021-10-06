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
    private final SpecialOfferService specialOfferService;

    public CartService(ShopUserService shopUserService, SpecialOfferService specialOfferService) {
        this.shopUserService = shopUserService;
        this.specialOfferService = specialOfferService;
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

    private void checkIfSpecialOffersAreAvaialable(ShoppingCart cart){
        if (cart.getSpecialOfferId()==0L){

        }
    }
}
