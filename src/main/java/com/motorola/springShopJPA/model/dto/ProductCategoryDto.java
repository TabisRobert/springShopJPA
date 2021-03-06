package com.motorola.springShopJPA.model.dto;

public class ProductCategoryDto {

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductCategoryDto() {
    }

    public ProductCategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
