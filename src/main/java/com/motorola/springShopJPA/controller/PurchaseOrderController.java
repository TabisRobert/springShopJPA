package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.PurchaseOrderDto;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.service.PurchaseOrderService;
import com.motorola.springShopJPA.service.ShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SessionAttributes("shopping_cart")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;
    private final ShopUserService shopUserService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService, ShopUserService shopUserService) {
        this.purchaseOrderService = purchaseOrderService;
        this.shopUserService = shopUserService;
    }

    @GetMapping("/show_orders")
    public String getPurchaseOrders(Model model){
        List<PurchaseOrderDto> allOrders = purchaseOrderService.getAllOrders();
        model.addAttribute("user", shopUserService.getDtoOfCurrentlyLoggedUser());
        model.addAttribute("orders", allOrders);
        return "order_view";
    }

    @PostMapping("/delete_order")
    public String deleteOrderById(@RequestParam("order_id") Long orderId) {
        purchaseOrderService.deletePurchaseOrderById(orderId);
        return "redirect:/admin_order_options";
    }
}
