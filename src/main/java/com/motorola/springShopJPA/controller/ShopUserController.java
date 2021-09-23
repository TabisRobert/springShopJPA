package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ShopUserDto;
import com.motorola.springShopJPA.service.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ShopUserController {


    private final ShopUserService shopUserService;

    @Autowired
    public ShopUserController(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    @GetMapping("/create_user")
    public ModelAndView getUserView(){
        return new ModelAndView("create_user", "user", new ShopUserDto());
    }

    @PostMapping("/add_user")
    public String addNewUser(@ModelAttribute @Valid ShopUserDto shopUserDto) {
        shopUserService.createNewUser(shopUserDto);
        return "redirect:/home";
    }
}
