package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.entity.Article;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.repository.ArticleRepository;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ArticleService {

    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ArticleService(ModelMapper modelMapper, ArticleRepository articleRepository, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
        this.productRepository = productRepository;
    }

    public ArticleDto addNewArticle(Long productId, Integer quantity) {
        Article newArticle = new Article();
        final Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(newArticle::setProduct);
        newArticle.setQuantity(quantity);
        newArticle.setArticleTotalPrice(newArticle.getProduct().getPrice().multiply(BigDecimal.valueOf(newArticle.getQuantity())));
        articleRepository.save(newArticle);
        Article recentlyAddedArticle = articleRepository.findTopByOrderByIdDesc();
        return modelMapper.map(recentlyAddedArticle, ArticleDto.class);
    }

    public void deleteArticleById(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    public void editArticle(Long articleId, Integer quantity) {
        if (quantity == 0) {
            deleteArticleById(articleId);
        }
        Optional<Article> existingArticle = articleRepository.findById(articleId);
        if (existingArticle.isPresent()) {
            Article editedArticle = existingArticle.get();
            editedArticle.setQuantity(quantity);
            editedArticle.setArticleTotalPrice(editedArticle.getProduct().getPrice().multiply(BigDecimal.valueOf(editedArticle.getQuantity())));
            articleRepository.save(editedArticle);
        }
    }

    public ArticleDto getArticleDtoById(Long articleId) {
        Optional<Article> existingArticle = articleRepository.findById(articleId);
        if (existingArticle.isPresent()) {
            return modelMapper.map(existingArticle.get(), ArticleDto.class);
        }
        throw new RuntimeException("Article with given ID doesn't exist in database");
    }
}
