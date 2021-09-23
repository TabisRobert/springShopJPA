package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ShopUserDto;
import com.motorola.springShopJPA.service.ProductCategoryService;
import com.motorola.springShopJPA.service.ProductService;
import com.motorola.springShopJPA.service.PurchaseOrderService;
import com.motorola.springShopJPA.service.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final ShopUserService shopUserService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final PurchaseOrderService purchaseOrderService;

    public AdminController(ShopUserService shopUserService, ProductService productService, PurchaseOrderService purchaseOrderService, ProductCategoryService productCategoryService) {
        this.shopUserService = shopUserService;
        this.productService = productService;
        this.purchaseOrderService = purchaseOrderService;
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/admin")
    public String getAdminView(){
        return "admin_view";
    }

    @GetMapping("/admin_add_new")
    public ModelAndView getAdminRegisterForm(){
        return new ModelAndView("admin_create_user", "user", new ShopUserDto());
    }

    @PostMapping("/admin_add_to_db")
    public String addNewAdmin(@ModelAttribute @Valid ShopUserDto shopUserDto) {
        shopUserService.createNewUser(shopUserDto);
        return "redirect:/admin";
    }

    @GetMapping("/admin_product_options")
    public String getProductsWithAdminOptions(Model model){
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", productCategoryService.getAllCategories());
        return "admin_product_crud";
    }
    @GetMapping("/admin_category_options")
    public String getCategoriesWithAdminOptions(Model model){
        model.addAttribute("categories", productCategoryService.getAllCategories());
        return "admin_category_crud";
    }
    @GetMapping("/admin_order_options")
    public String getCategoriesWithAdminOptions(Model model){
        model.addAttribute("orders", purchaseOrderService.getAllOrders());
        return "admin_order_crud";
    }
}
