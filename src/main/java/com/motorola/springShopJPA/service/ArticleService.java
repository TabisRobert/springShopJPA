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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

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

    public ArticleDto addNewArticle(Long productId, Integer quantity, Map<Long, ArticleDto> articles) {
        if (articles!=null && articles.size()>0){
            final Set<Map.Entry<Long, ArticleDto>> cartArticlesEntries = articles.entrySet();
            for (Map.Entry<Long, ArticleDto> entry : cartArticlesEntries) {
                if (entry.getValue().getProduct().getId().equals(productId)){
                    final Optional<Article> editedArticle = articleRepository.findById(entry.getKey());
                    if (editedArticle.isPresent()){
                        final Article article = editedArticle.get();
                        editArticle(article.getId(),quantity);
                        return modelMapper.map(article, ArticleDto.class);
                    }
                }
            }
        }
        if (articles == null || articles.size()>0){
            Article newArticle = new Article();
            final Optional<Product> product = productRepository.findById(productId);
            product.ifPresent(newArticle::setProduct);
            newArticle.setQuantity(quantity);
            newArticle.setOrdered(false);
            newArticle.setArticleTotalPrice(newArticle.getProduct().getPrice().multiply(BigDecimal.valueOf(newArticle.getQuantity())));
            product.ifPresent(product1 -> product1.getArticles().add(newArticle));
            articleRepository.save(newArticle);
            Article recentlyAddedArticle = articleRepository.findTopByOrderByIdDesc();
            return modelMapper.map(recentlyAddedArticle, ArticleDto.class);
        }
        throw new RuntimeException("Błąd");
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
