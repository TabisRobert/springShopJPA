package com.motorola.springShopJPA.repository;

import com.motorola.springShopJPA.model.entity.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
    Article findTopByOrderByIdDesc(); //finds first element from the table sorted by descending order == finds element with last id
}
