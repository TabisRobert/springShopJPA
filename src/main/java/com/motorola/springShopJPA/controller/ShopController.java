package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.service.ProductService;
import com.motorola.springShopJPA.service.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    private final ShopUserService shopUserService;
    private final ProductService productService;

    @Autowired
    public ShopController(ShopUserService shopUserService, ProductService productService) {
        this.shopUserService = shopUserService;
        this.productService = productService;
    }

    @GetMapping("/index")
    public String homePageForLoggedUser(Model model){
        model.addAttribute("shop_user", shopUserService.getDtoOfCurrentlyLoggedUser());
        model.addAttribute("products", productService.getAllProducts());
        return "shop_index";
    }

    @GetMapping("/home")
    public String homePageForGuest(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "home_page";
    }
}
