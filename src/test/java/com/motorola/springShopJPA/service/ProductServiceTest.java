package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ProductCategoryDto;
import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.model.entity.ProductCategory;
import com.motorola.springShopJPA.repository.ProductCategoryRepository;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.aspectj.bridge.IMessage;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private static final ProductCategory CATEGORY = new ProductCategory(1L, "FOOD");
    private static final ProductCategoryDto CATEGORY_DTO = new ProductCategoryDto(1L, "FOOD");
    private static final Product PRODUCT = new Product(1L,"Product","opis", BigDecimal.TEN, CATEGORY);
    private static final ProductDto PRODUCT_DTO = new ProductDto(1L,"Product","opis", BigDecimal.TEN, CATEGORY_DTO);


    private final ModelMapper modelMapper = mock(ModelMapper.class);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductCategoryRepository productCategoryRepository = mock(ProductCategoryRepository.class);

    private final ProductService productService = new ProductService(modelMapper, productRepository, productCategoryRepository);

    @Test
    public void shouldReturnEmptyList_whenThereAreNoProducts(){
        //given
        List<ProductDto> result = productService.getAllProducts();
        //when
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnListWithOneProduct(){
        //given
        given(productService.getAllProducts()).willReturn(List.of(PRODUCT_DTO));
        given(productRepository.findAll()).willReturn(List.of(PRODUCT));
        //when
        List<ProductDto> result = productService.getAllProducts();
        //then
        assertThat(result).hasSize(1);
    }

    @Test
    public void shouldReturnListWithProductMappedToProductDto(){
        //given
        given(productService.getAllProducts()).willReturn(List.of(PRODUCT_DTO));
        given(productRepository.findAll()).willReturn(List.of(PRODUCT));
        given(modelMapper.map(PRODUCT, ProductDto.class)).willReturn(PRODUCT_DTO);
        //when
        List<ProductDto> result = productService.getAllProducts();
        //then
        assertThat(result.get(0)).isEqualTo(PRODUCT_DTO);

    }

    @Test
    public void shouldReturnProductDtoFromGivenProduct(){
        //given
        given(modelMapper.map(PRODUCT, ProductDto.class)).willReturn(PRODUCT_DTO);
        //when
        ProductDto result = productService.getProductDto(PRODUCT);
        //then
        assertThat(result).isEqualTo(PRODUCT_DTO);
    }

    @Test
    public void shouldFindProductDtoByGivenId(){
        //given
        Long id = 1L;
        given(modelMapper.map(PRODUCT, ProductDto.class)).willReturn(PRODUCT_DTO);
        given(productRepository.findById(id)).willReturn(Optional.of(PRODUCT));
        //when
        ProductDto result = productService.getProductById(id);
        //then
        assertThat(result).isEqualTo(PRODUCT_DTO);
    }

    @Test
    public void shouldThrowRuntimeException_whenProductOfGivenIdDoesNotExist(){
        //given
        Long id = 2L;
        String exceptionMessage = "Product does not exist";
        given(productRepository.findById(id)).willReturn(Optional.ofNullable(null));
        //when
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> productService.getProductById(id));
        //then
        assertEquals(exception.getMessage(), exceptionMessage);
    }

    @Test
    public void shouldCreateNewProductFromGivenProductDtoAndCategory(){
        //given
        String name = "FOOD";
        given(modelMapper.map(PRODUCT_DTO, Product.class)).willReturn(PRODUCT);
        given(productCategoryRepository.findByName(name)).willReturn(CATEGORY);
        //when
        productService.createNewProduct(PRODUCT_DTO, name);
        //then
        verify(productRepository, times(1)).save(PRODUCT);
    }

    @Test
    public void shouldDeleteProductByGivenId(){
        //given
        Long id = 1L;
        //when
        productService.deleteProduct(id);
        //then
        verify(productRepository, times(1)).deleteById(id);
    }
    @Test
    public void shouldThrowRuntimeException_whenTryingToEditNonexistingProduct(){
        //given
        Long id = 2L;
        String name = "FOOD";
        String exceptionMessage = "Product does not exist";

        ProductDto productDto = new ProductDto();
        productDto.setId(id);

        given(productRepository.findById(id)).willReturn(Optional.ofNullable(null));
        //when
        RuntimeException exception = assertThrows(RuntimeException.class, ()-> productService.editProduct(productDto, name));
        //then
        assertEquals(exception.getMessage(), exceptionMessage);
    }

    @Test
    public void shouldEditExistingProduct(){
        //given
        Long id = 1L;
        String name = "FOOD";
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        given(productRepository.findById(id)).willReturn(Optional.of(PRODUCT));
        given(productCategoryRepository.findByName(name)).willReturn(CATEGORY);
        //when
        productService.editProduct(productDto, name);
        //then
        verify(productRepository, times(1)).findById(id);
        verify(productCategoryRepository, times(1)).findByName(name);
        verify(productRepository, times(1)).save(PRODUCT);
    }
}
