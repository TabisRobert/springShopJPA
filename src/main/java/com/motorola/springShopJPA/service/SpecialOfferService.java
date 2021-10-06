package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.dto.SpecialOfferDto;
import com.motorola.springShopJPA.model.entity.Article;
import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.model.entity.SpecialOffer;
import com.motorola.springShopJPA.model.enums.SpecialOfferDiscountType;
import com.motorola.springShopJPA.repository.SpecialOfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class SpecialOfferService {

    private final ModelMapper modelMapper;
    private final SpecialOfferRepository specialOfferRepository;


    @Autowired
    public SpecialOfferService(ModelMapper modelMapper, SpecialOfferRepository specialOfferRepository) {
        this.modelMapper = modelMapper;
        this.specialOfferRepository = specialOfferRepository;
    }

    public List<SpecialOfferDto> getAllSpecialOffers() {
        List<SpecialOffer> specialOffers = (List<SpecialOffer>) specialOfferRepository.findAll();
        return specialOffers.stream()
                .map(offer -> modelMapper.map(offer, SpecialOfferDto.class))
                .collect(Collectors.toList());
    }

    public void createNewSpecialOffer(SpecialOfferDto specialOfferDto, SpecialOfferDiscountType discountType) {
        final SpecialOffer specialOffer = modelMapper.map(specialOfferDto, SpecialOffer.class);
        specialOffer.setDiscountType(discountType);
        if (discountType == SpecialOfferDiscountType.FREE_PRODUCT) {
            specialOffer.setDiscountPercent(BigDecimal.ZERO);
        } else {
            specialOffer.setNumberOfFreeProducts((short) 0);
        }
        specialOfferRepository.save(specialOffer);
    }

    public void deleteSpecialOffer(Long id) {
        final Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(id);
        if (specialOffer.isPresent() && specialOffer.get().getPurchaseOrderSet() != null && specialOffer.get().getPurchaseOrderSet().size() > 0) {
            final Set<PurchaseOrder> purchaseOrderSet = specialOffer.get().getPurchaseOrderSet();
            for (PurchaseOrder order : purchaseOrderSet) {
                order.setSpecialOffer(null);
                final Optional<BigDecimal> totalPrice = order.getArticles().values().stream()
                        .map(Article::getArticleTotalPrice)
                        .reduce(BigDecimal::add);
                totalPrice.ifPresent(order::setTotalValue);
            }
        }
        specialOfferRepository.deleteById(id);
    }

    public SpecialOfferDiscountType[] getAllDiscountTypes() {
        return SpecialOfferDiscountType.values();
    }

    public void checkDiscountForTenFoodArticles(PurchaseOrder purchaseOrder) {
        int foodArticles = 0;
        final Set<Map.Entry<Long, Article>> articleEntries = purchaseOrder.getArticles().entrySet();
        for (Map.Entry<Long, Article> entry : articleEntries) {
            if (checkIfProductBelongsToCategory(entry, "Food")) {
                foodArticles += entry.getValue().getQuantity();
            }
        }
        if (foodArticles >= 10) {
            final SpecialOffer specialOffer = specialOfferRepository.findByName("10for10");
            purchaseOrder.setSpecialOffer(specialOffer);
            final BigDecimal discountPercent = purchaseOrder.getSpecialOffer().getDiscountPercent();
            purchaseOrder.setTotalValue(purchaseOrder.getTotalValue().multiply(BigDecimal.ONE.subtract(discountPercent)));
        }
    }

    public void checkDiscountForThreeCosmetics(PurchaseOrder purchaseOrder) {
        boolean areThreeCosmeticsInOrder = false;
        Long articleId = 0L;
        final Set<Map.Entry<Long, Article>> articleEntries = purchaseOrder.getArticles().entrySet();
        for (Map.Entry<Long, Article> entry : articleEntries) {
            if (checkIfProductBelongsToCategory(entry, "Cosmetics") && entry.getValue().getQuantity() >= 3) {
                articleId = entry.getKey();
                areThreeCosmeticsInOrder = true;
                break;
            }
        }
        if (areThreeCosmeticsInOrder) {
            final SpecialOffer specialOffer = specialOfferRepository.findByName("cosm3for2");
            purchaseOrder.setSpecialOffer(specialOffer);
            calculateTotalPriceWithoutFreeProducts(purchaseOrder, articleId);
        }
    }

    private boolean checkIfProductBelongsToCategory(Map.Entry<Long, Article> entry, String categoryName) {
        return entry.getValue().getProduct().getProductCategory().getName().equalsIgnoreCase(categoryName);
    }

    public void calculateTotalPriceWithoutFreeProducts(PurchaseOrder order, Long articleId) {
        Set<Map.Entry<Long, Article>> articlesSet = order.getArticles().entrySet();
        Optional<BigDecimal> totalPrice = articlesSet.stream()
                .map(entry -> entry.getValue().getArticleTotalPrice())
                .reduce(BigDecimal::add);
        if (totalPrice.isPresent()) {
            final BigDecimal totalValue = totalPrice.get().subtract(getPriceOfOneProduct(order, articleId));
            order.setTotalValue(totalValue);
        } else {
            order.setTotalValue(BigDecimal.ZERO);
        }
    }

    private BigDecimal getPriceOfOneProduct(PurchaseOrder order, Long articleId) {
        return order.getArticles().get(articleId).getProduct().getPrice();
    }
}
