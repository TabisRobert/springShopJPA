package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
