package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.dto.PurchaseOrderDto;
import com.motorola.springShopJPA.model.dto.ShopUserDto;
import com.motorola.springShopJPA.model.entity.Article;
import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import com.motorola.springShopJPA.model.entity.ShopUser;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.repository.ArticleRepository;
import com.motorola.springShopJPA.repository.PurchaseOrderRepository;
import com.motorola.springShopJPA.repository.ShopUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ArticleRepository articleRepository;
    private final ShopUserRepository shopUserRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, ArticleRepository articleRepository, ShopUserRepository shopUserRepository, ModelMapper modelMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.articleRepository = articleRepository;
        this.shopUserRepository = shopUserRepository;
        this.modelMapper = modelMapper;
    }

    public void createNewOrderFromCart(ShoppingCart shoppingCart) {
        Map<Long, Article> articleMap = new HashMap<>();
        BigDecimal totalValue = BigDecimal.ZERO;
        for (Long key: shoppingCart.getArticles().keySet()) {
            Optional<Article> article = articleRepository.findById(key);
            article.ifPresent(article1 -> totalValue.add(article.get().getArticleTotalPrice()));
            article.ifPresent(value -> articleMap.put(key, value));
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setArticles(articleMap);
        Optional<ShopUser> shopUser = shopUserRepository.findById(shoppingCart.getShopUserId());
        shopUser.ifPresent(purchaseOrder::setShopUser);
        purchaseOrder.setTotalValue(totalValue);
        purchaseOrderRepository.save(purchaseOrder);

    }

    public List<PurchaseOrderDto> getAllOrders(){
        List<PurchaseOrder> orders = (List<PurchaseOrder>) purchaseOrderRepository.findAll();
        List<PurchaseOrderDto> orderDtos = new ArrayList<>();
        for (PurchaseOrder order: orders) {
            PurchaseOrderDto orderDto = new PurchaseOrderDto();
            orderDto.setId(order.getId());
            orderDto.setShopUser(modelMapper.map(order.getShopUser(), ShopUserDto.class));
            orderDto.setTotalValue(order.getTotalValue());
            Map<Long, ArticleDto> articleDtoMap = new HashMap<>();
            for (Long key: order.getArticles().keySet()){
                articleDtoMap.put(key, modelMapper.map(order.getArticles().get(key), ArticleDto.class));
            }
            orderDto.setArticles(articleDtoMap);
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }
}
