package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
}
