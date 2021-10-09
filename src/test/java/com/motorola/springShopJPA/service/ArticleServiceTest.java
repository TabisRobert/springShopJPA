package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.entity.Article;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.repository.ArticleRepository;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ArticleServiceTest {

    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final ArticleRepository articleRepository = mock(ArticleRepository.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final ArticleService articleService = new ArticleService(modelMapper, articleRepository, productRepository);

    private final Product product = new Product(1L,"Product","opis", BigDecimal.TEN, null);
    private final ArticleDto ARTICLE_DTO = new ArticleDto(1L, product,1,BigDecimal.TEN,false);
    private  final Article ARTICLE = new Article(1L, product,1,BigDecimal.TEN,false);

    @Test
    public void shouldThrowRuntimeException_whenMapIsEmpty(){
        //given
        String exceptionMessage = "Błąd";
        Map<Long, ArticleDto> emptyMap = new HashMap<>();
        //when
        RuntimeException exception = assertThrows(RuntimeException.class, ()->articleService.addNewArticle(1L, 1,emptyMap));
        //then
        assertEquals(exception.getMessage(), exceptionMessage);
    }

    @Test
    public void shouldAddNewArticleAndReturnItsDto_whenArticleDoesNotExist(){
        //given
        Long productId = 1L;
        product.setArticles(new HashSet<>());
        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        given(articleRepository.findTopByOrderByIdDesc()).willReturn(ARTICLE);
        given(modelMapper.map(ARTICLE, ArticleDto.class)).willReturn(ARTICLE_DTO);
        //when
        ArticleDto articleDto = articleService.addNewArticle(productId, 1,null);
        //then
        assertThat(articleDto).isEqualTo(ARTICLE_DTO);
    }
    @Test
    public void shouldEditArticlesQuantityAndReturnItsDto_whenArticleExists(){
        //given
        Long id = 1L;
        int quantity = 2;
        Map<Long, ArticleDto> articleMap = new HashMap<>();
        ArticleDto editedDto = new ArticleDto(id, product,quantity,BigDecimal.TEN,false);
        articleMap.put(ARTICLE_DTO.getId(), ARTICLE_DTO);
        given(articleRepository.findById(id)).willReturn(Optional.of(ARTICLE));
        given(modelMapper.map(ARTICLE, ArticleDto.class)).willReturn(editedDto);
        //when
        ArticleDto articleDto = articleService.addNewArticle(id, quantity, articleMap);
        //then
        assertThat(articleDto).isEqualTo(editedDto);
        assertThat(articleDto.getQuantity()).isEqualTo(editedDto.getQuantity());
    }
    @Test
    public void shouldDeleteArticle_whenNewQuantityEqualsZero(){
        //given
        Long id = 1L;
        int quantity = 0;
        Map<Long, ArticleDto> articleMap = new HashMap<>();
        ArticleDto editedDto = new ArticleDto(id, product,quantity,BigDecimal.TEN,false);
        articleMap.put(ARTICLE_DTO.getId(), ARTICLE_DTO);
        given(articleRepository.findById(id)).willReturn(Optional.of(ARTICLE));
        given(modelMapper.map(ARTICLE, ArticleDto.class)).willReturn(editedDto);
        //when
        articleService.addNewArticle(id, quantity, articleMap);
        //then
        verify(articleRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldReturnArticleDtoFromArticleFoundByGivenId(){
        //given
        Long id = 1L;
        given(articleRepository.findById(id)).willReturn(Optional.of(ARTICLE));
        given(modelMapper.map(ARTICLE, ArticleDto.class)).willReturn(ARTICLE_DTO);
        //when
        ArticleDto result = articleService.getArticleDtoById(id);
        //then
        assertThat(result).isEqualTo(ARTICLE_DTO);
    }
    @Test
    public void shouldEditArticle(){
        //given
        Long id = 1L;
        int quantity = 2;
        given(articleRepository.findById(id)).willReturn(Optional.of(ARTICLE));
        //when
        articleService.editArticle(id,quantity);
        //then
        verify(articleRepository, times(1)).save(ARTICLE);
    }
    @Test
    public void shouldDeleteEditedArticle_whenQuantityEqualsZero(){
        //given
        Long id = 1L;
        int quantity = 0;
        given(articleRepository.findById(id)).willReturn(Optional.of(ARTICLE));
        //when
        articleService.editArticle(id,quantity);
        //then
        verify(articleRepository, times(1)).deleteById(id);
    }
}
