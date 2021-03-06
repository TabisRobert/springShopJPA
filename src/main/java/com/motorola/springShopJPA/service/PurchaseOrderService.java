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

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ArticleRepository articleRepository;
    private final ShopUserRepository shopUserRepository;
    private final ModelMapper modelMapper;
    private final SpecialOfferService specialOfferService;


    @Autowired
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, ArticleRepository articleRepository, ShopUserRepository shopUserRepository, ModelMapper modelMapper, SpecialOfferService specialOfferService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.articleRepository = articleRepository;
        this.shopUserRepository = shopUserRepository;
        this.modelMapper = modelMapper;
        this.specialOfferService = specialOfferService;
    }

    public void createNewOrderFromCart(ShoppingCart shoppingCart) {
        Optional<ShopUser> shopUser = shopUserRepository.findById(shoppingCart.getShopUserId());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        shopUser.ifPresent(purchaseOrder::setShopUser);
        Map<Long, Article> articleMap = new HashMap<>();
        savePurchaseOrderToDatabase(purchaseOrder);

        purchaseOrder = purchaseOrderRepository.findTopByOrderByIdDesc();
        for (Long key: shoppingCart.getArticles().keySet()) {
            Optional<Article> article = articleRepository.findById(key);
            if (article.isPresent()){
                Article orderedArticle = article.get();
                articleMap.put(key, orderedArticle);
                orderedArticle.setOrdered(true);
                orderedArticle.setPurchaseOrder(purchaseOrder);
                articleRepository.save(orderedArticle);
            }
        }
        purchaseOrder.setArticles(articleMap);
        final BigDecimal cartTotalValue = shoppingCart.getTotalValue();
        purchaseOrder.setTotalValue(cartTotalValue);
        checkForSpecialOffers(purchaseOrder);
        savePurchaseOrderToDatabase(purchaseOrder);

    }

    private void checkForSpecialOffers(PurchaseOrder purchaseOrder) {
        specialOfferService.checkDiscountForTenFoodArticles(purchaseOrder);
        specialOfferService.checkDiscountForThreeCosmetics(purchaseOrder);
    }

    private void savePurchaseOrderToDatabase(PurchaseOrder purchaseOrder) {
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

    public void deletePurchaseOrderById(Long id){
        final Optional<PurchaseOrder> existingOrder = purchaseOrderRepository.findById(id);
        if (existingOrder.isPresent()){
            final Map<Long, Article> articles = existingOrder.get().getArticles();
            for (Map.Entry<Long,Article> entry: articles.entrySet()){
                articleRepository.deleteById(entry.getKey());
            }
            articles.clear();
        }
        purchaseOrderRepository.deleteById(id);
    }
}
