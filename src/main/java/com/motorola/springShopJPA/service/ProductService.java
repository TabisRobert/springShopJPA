package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.model.entity.ProductCategory;
import com.motorola.springShopJPA.repository.ProductCategoryRepository;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ModelMapper modelMapper, ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    private ProductDto getProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public void createNewProduct(ProductDto productDto, String categoryName){
        Product product = modelMapper.map(productDto, Product.class);
        product.setProductCategory(productCategoryRepository.findByName(categoryName));
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void editProduct(ProductDto productDto, String categoryName){
        Optional<Product> existingProduct = productRepository.findById(productDto.getId());
        if (existingProduct.isPresent()){
            Product editedProduct = existingProduct.get();
            editedProduct.setDescription(productDto.getDescription());
            editedProduct.setName(productDto.getName());
            editedProduct.setPrice(productDto.getPrice());
            editedProduct.setProductCategory(productCategoryRepository.findByName(categoryName));
            productRepository.save(editedProduct);
        }
    }


    public ProductDto getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return getProductDto(product.get());
        }
        throw new RuntimeException("Product does not exist");
    }
}
