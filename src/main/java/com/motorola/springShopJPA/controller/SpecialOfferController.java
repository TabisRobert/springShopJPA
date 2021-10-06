package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.dto.SpecialOfferDto;
import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;
import com.motorola.springShopJPA.service.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpecialOfferController {

    private final SpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferController(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    @PostMapping("/add_offer")
    public String addNewProduct(@ModelAttribute SpecialOfferDto specialOfferDto, @RequestParam("discount_type")SpecialOfferDiscountType discountType){
        specialOfferService.createNewSpecialOffer(specialOfferDto, discountType);
        return "redirect:/admin_discount_options";
    }
    @PostMapping("/delete_offer")
    public String deleteProduct(@RequestParam("special_offer_id") Long id){
        specialOfferService.deleteSpecialOffer(id);
        return "redirect:/admin_discount_options";
    }
}
