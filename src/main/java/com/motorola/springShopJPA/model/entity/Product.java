package com.motorola.springShopJPA.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product")
    private Set<Article> articles;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }
}
