package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.SpecialOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialOfferRepository extends CrudRepository<SpecialOffer, Long> {
}
