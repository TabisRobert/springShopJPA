package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticeRepository extends CrudRepository<Article, Long> {
}
