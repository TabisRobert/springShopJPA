package com.motorola.springShopJPA.model.entity;


import java.util.Map;

public class Cart {

    private Long shopUserId;
    private Map<Long, Article> articles;

}
