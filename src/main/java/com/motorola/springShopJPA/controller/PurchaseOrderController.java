package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.PurchaseOrderDto;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @Autowired
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/show_orders")
    public String getPurchaseOrders(Model model){
        List<PurchaseOrderDto> allOrders = purchaseOrderService.getAllOrders();
        model.addAttribute("orders", allOrders);
        return "order_view";
    }
}
