package com.motorola.springShopJPA.controller;

import com.motorola.springShopJPA.model.dto.ArticleDto;
import com.motorola.springShopJPA.model.entity.ShoppingCart;
import com.motorola.springShopJPA.service.ArticleService;
import com.motorola.springShopJPA.service.CartService;
import com.motorola.springShopJPA.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("shopping_cart")
public class CartController {

    private final CartService cartService;
    private final ArticleService articleService;

    @Autowired
    public CartController(CartService cartService, ArticleService articleService) {
        this.cartService = cartService;
        this.articleService = articleService;
    }

    @PostMapping("/add_to_cart")
    public String addArticleToCart(Model model, @ModelAttribute("shopping_cart") ShoppingCart shoppingCart, @RequestParam("product_id") Long productId, @RequestParam("product_quantity") Integer quantity) {
        if (shoppingCart != null && shoppingCart.getShopUserId() != null) {
            ArticleDto articleDto = articleService.addNewArticle(productId, quantity);
            shoppingCart.getArticles().put(articleDto.getId(), articleDto);
            model.addAttribute("shopping_cart", shoppingCart);
        } else {
            ShoppingCart cart = cartService.createNewCart();
            Map<Long, ArticleDto> articleDtoMap = new HashMap<>();
            ArticleDto articleDto = articleService.addNewArticle(productId, quantity);
            articleDtoMap.put(articleDto.getId(), articleDto);
            cart.setArticles(articleDtoMap);
            model.addAttribute("shopping_cart", cart);
        }
        return "redirect:/index";
    }

    @GetMapping("/cart")
    public String getCart(@SessionAttribute("shopping_cart") ShoppingCart cart, Model model) {
        model.addAttribute("cart", cart);
        return "cart_view";
    }

    @ModelAttribute("shopping_cart")
    public ShoppingCart shoppingCart() {
        return new ShoppingCart();
    }

    @PostMapping("/edit_article")
    public String eitArticle(Model model, @ModelAttribute("shopping_cart") ShoppingCart shoppingCart, @RequestParam("article_id") Long articleId, @RequestParam("article_quantity") Integer quantity) {
        articleService.editArticle(articleId, quantity);
        if (quantity==0){
            shoppingCart.getArticles().remove(articleId);
        } else {
            ArticleDto editedArticle = articleService.getArticleDtoById(articleId);
            shoppingCart.getArticles().put(articleId, editedArticle);
        }
        model.addAttribute("shopping_cart", shoppingCart);
        return "redirect:/cart";
    }

    @PostMapping("/delete_article")
    public String deleteArticle(Model model, @ModelAttribute("shopping_cart") ShoppingCart shoppingCart, @RequestParam("article_id") Long articleId) {
        shoppingCart.getArticles().remove(articleId);
        articleService.deleteArticleById(articleId);
        model.addAttribute("shopping_cart", shoppingCart);
        return "redirect:/cart";
    }
}
