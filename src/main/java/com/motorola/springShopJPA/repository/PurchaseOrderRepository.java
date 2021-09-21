package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
}
