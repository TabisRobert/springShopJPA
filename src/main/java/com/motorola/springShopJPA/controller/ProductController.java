package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add_product")
    public String addNewProduct(@ModelAttribute ProductDto productDto, @RequestParam("product_category") String categoryName){
        productService.createNewProduct(productDto, categoryName);
        return "redirect:/admin_product_options";
    }

    @PostMapping("/edit_product")
    public String editProduct(@ModelAttribute ProductDto productDto, @RequestParam("product_category") String categoryName){
        productService.editProduct(productDto, categoryName);
        return "redirect:/admin_product_options";
    }
    @PostMapping("/delete_product")
    public String deleteProduct(@RequestParam("product_id") Long id){
        productService.deleteProduct(id);
        return "redirect:/admin_product_options";
    }
}
