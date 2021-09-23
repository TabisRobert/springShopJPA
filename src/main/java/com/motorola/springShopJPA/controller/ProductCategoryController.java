package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ProductCategoryDto;
import com.motorola.springShopJPA.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/add_category")
    public String addNewCategory(@ModelAttribute ProductCategoryDto categoryDto){
        productCategoryService.createNewCategory(categoryDto);
        return "redirect:/admin_category_options";
    }
    @PostMapping("/edit_category")
    public String editCategory(@ModelAttribute ProductCategoryDto categoryDto){
        productCategoryService.editProductCategory(categoryDto);
        return "redirect:/admin_category_options";
    }
    @PostMapping("/delete_category")
    public String deleteProduct(@RequestParam("category_id") Long id){
        productCategoryService.deleteProductCategory(id);
        return "redirect:/admin_category_options";
    }
}
