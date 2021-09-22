package com.motorola.springShopJPA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController {

    @GetMapping("/index")
    public String home(){
        return "shop_index";
    }
}
