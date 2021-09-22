package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.ShopUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopUserRepository extends CrudRepository<ShopUser, Long> {
    ShopUser findByEmail(String username);
}
