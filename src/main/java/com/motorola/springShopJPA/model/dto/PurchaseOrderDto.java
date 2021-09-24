package com.motorola.springShopJPA.model.dto;

import java.math.BigDecimal;
import java.util.Map;

public class PurchaseOrderDto {

    private Long id;
    private Map<Long, ArticleDto> articles;
    private ShopUserDto shopUser;
    private SpecialOfferDto specialOffer;
    private BigDecimal totalValue;
}
