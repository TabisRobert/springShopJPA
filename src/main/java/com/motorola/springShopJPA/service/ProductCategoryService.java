package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ProductCategoryDto;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.model.entity.ProductCategory;
import com.motorola.springShopJPA.repository.ProductCategoryRepository;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    private final ModelMapper modelMapper;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductCategoryService(ModelMapper modelMapper, ProductCategoryRepository productCategoryRepository, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }


    public List<ProductCategoryDto> getAllCategories() {
        List<ProductCategory> categories = getCategoriesList();
        List<ProductCategoryDto> categoryDtos = new ArrayList<>();
        for (ProductCategory productCategory : categories){
            ProductCategoryDto categoryDto = getCategoryDto(productCategory);
            categoryDtos.add(categoryDto);
        }
        return categoryDtos;
    }

    private ProductCategoryDto getCategoryDto(ProductCategory productCategory) {
        return modelMapper.map(productCategory, ProductCategoryDto.class);
    }
    public void createNewCategory(ProductCategoryDto categoryDto){
        ProductCategory category = modelMapper.map(categoryDto, ProductCategory.class);
        productCategoryRepository.save(category);
    }
    public void deleteProductCategory(Long id){
        final String defaultCategoryName = "Default";
        if (!checkIfCategoryExist(defaultCategoryName)){
            ProductCategoryDto defaultCategory = new ProductCategoryDto();
            defaultCategory.setName(defaultCategoryName);
            createNewCategory(defaultCategory);
        }
        Optional<ProductCategory> category = productCategoryRepository.findById(id);
        List<Product> allProducts = (List<Product>) productRepository.findAll();
        for (Product product: allProducts) {
            if (category.isPresent() && product.getProductCategory().equals(category)){
                product.setProductCategory(productCategoryRepository.findByName("Default"));
            }
        }
        productCategoryRepository.deleteById(id);
    }

    private boolean checkIfCategoryExist(String categoryname){
        List<ProductCategory> categories = getCategoriesList();
        for (ProductCategory category: categories) {
            if (category.getName().equals(categoryname)){
                return true;
            }
        }
        return false;
    }

    private List<ProductCategory> getCategoriesList() {
        return (List<ProductCategory>) productCategoryRepository.findAll();
    }

    public void editProductCategory(ProductCategoryDto categoryDto){
        Optional<ProductCategory> existingCategory = productCategoryRepository.findById(categoryDto.getId());
        if (existingCategory.isPresent()){
            ProductCategory editedCategory = existingCategory.get();
            editedCategory.setName(categoryDto.getName());
            productCategoryRepository.save(editedCategory);
        }
    }

    public ProductCategoryDto getCategoryById(Long id) {
        Optional<ProductCategory> category = productCategoryRepository.findById(id);
        if (category.isPresent()) {
            return getCategoryDto(category.get());
        }
        throw new RuntimeException("Product does not exist");
    }
}
