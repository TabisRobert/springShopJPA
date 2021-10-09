package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.model.entity.ShopUser;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.model.enums.UserRole;
import com.motorola.springShopJPA.repository.ShopUserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CartServiceTest {

    private final Product product = new Product(1L,"product1","description1", BigDecimal.TEN,null);
    private static final ShopUser shopUser = new ShopUser(1L,"asd@asd.asd","asdasd", "ROLE_ADMIN",null);

    private final ShopUserService shopUserService = mock(ShopUserService.class);
    private final ShopUserRepository shopUserRepository = mock(ShopUserRepository.class);
    private final Authentication authentication = mock(Authentication.class);
    private final SecurityContext securityContext =mock(SecurityContext.class);
    private final UserDetails userDetails = mock(UserDetails.class);


    private final CartService cartService = new CartService(shopUserService);


    @Test
    public void shouldReturnNewShoppingCart(){
        //given
        String username = "asd@asd.asd";
        SecurityContextHolder.setContext(securityContext);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userDetails);
        given(userDetails.getUsername()).willReturn(username);
        given(shopUserRepository.findByEmail(username)).willReturn(shopUser);
        given(shopUserService.findCurrentlyLoggedUser()).willReturn(shopUser);
        //when
        ShoppingCart result = cartService.createNewCart();
        //then
        assertNotNull(result);
        assertThat(result.getShopUserId()).isEqualTo(shopUser.getId());
    }

    @Test
    public void shouldCalculateTotalPriceOfArticlesInCart(){
        //given
        int articleQuantity = 1;
        int numberOfArticles = 5;
        ShoppingCart shoppingCart = new ShoppingCart();
        Map<Long, ArticleDto> articleDtoMap = new HashMap<>();
        ArticleDto articleDto = new ArticleDto();
        for (long i = 0; i < numberOfArticles; i++){
            articleDto.setId(i);
            articleDto.setProduct(product);
            articleDto.setQuantity(articleQuantity);
            articleDto.setArticleTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(articleQuantity)));
            articleDtoMap.put(i, articleDto);
        }
        //when
        shoppingCart.setArticles(articleDtoMap);
        cartService.calculateTotalPriceOfArticlesInCart(shoppingCart);
        //then
        assertThat(shoppingCart.getTotalValue()).isEqualTo(product.getPrice().multiply(BigDecimal.valueOf(numberOfArticles)));
    }
}
