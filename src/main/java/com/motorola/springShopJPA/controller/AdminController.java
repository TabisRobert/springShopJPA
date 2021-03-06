package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ProductCategoryDto;
import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.dto.ShopUserDto;
import com.motorola.springShopJPA.model.dto.SpecialOfferDto;
import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;
import com.motorola.springShopJPA.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final ShopUserService shopUserService;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final PurchaseOrderService purchaseOrderService;
    private final SpecialOfferService specialOfferService;

    public AdminController(ShopUserService shopUserService, ProductService productService, PurchaseOrderService purchaseOrderService, ProductCategoryService productCategoryService, SpecialOfferService specialOfferService) {
        this.shopUserService = shopUserService;
        this.productService = productService;
        this.purchaseOrderService = purchaseOrderService;
        this.productCategoryService = productCategoryService;
        this.specialOfferService = specialOfferService;
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
        model.addAttribute("new_product", new ProductDto());
        return "admin_product_crud";
    }
    @GetMapping("/admin_category_options")
    public String getCategoriesWithAdminOptions(Model model){
        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("new_category", new ProductCategoryDto());
        return "admin_category_crud";
    }
    @GetMapping("/admin_order_options")
    public String getOrdersWithAdminOptions(Model model){
        model.addAttribute("orders", purchaseOrderService.getAllOrders());
        return "admin_order_crud";
    }
    @GetMapping("admin_discount_options")
    public String getSpecialOffersWithAdminOptions(Model model){
        SpecialOfferDiscountType[] offerDiscountTypes = specialOfferService.getAllDiscountTypes();
        model.addAttribute("special_offers", specialOfferService.getAllSpecialOffers());
        model.addAttribute("new_offer", new SpecialOfferDto());
        model.addAttribute("discount_types", offerDiscountTypes);
        return  "admin_special_offer_crud";
    }

    @PostMapping("/product_edition")
    public String openProductEditForm(@RequestParam("product_id") Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", productCategoryService.getAllCategories());
        model.addAttribute("category", new ProductCategoryDto());
        return "admin_product_edit";
    }

    @PostMapping("/category_edition")
    public String openCategoryEditForm(@RequestParam("category_id") Long id, Model model){
        model.addAttribute("category", productCategoryService.getCategoryById(id));
        return "admin_category_edit";
    }

}
