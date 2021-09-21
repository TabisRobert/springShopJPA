package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.ShopUser;
import org.springframework.data.repository.CrudRepository;

public interface ShopUserRepository extends CrudRepository<ShopUser, Long> {
}
