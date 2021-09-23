package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
    ProductCategory findByName(String name);
}
