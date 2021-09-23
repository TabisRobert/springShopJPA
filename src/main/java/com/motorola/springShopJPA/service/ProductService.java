package com.motorola.springShopJPA.service;

import com.motorola.springShopJPA.model.dto.ProductDto;
import com.motorola.springShopJPA.model.entity.Product;
import com.motorola.springShopJPA.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }


    public List<ProductDto> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products){
            ProductDto productDto = getProductDto(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    private ProductDto getProductDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public void createNewProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public void editProduct(ProductDto productDto){
        Optional<Product> existingProduct = productRepository.findById(productDto.getId());
        if (existingProduct.isPresent()){
            Product editedProduct = existingProduct.get();
            editedProduct.setDescription(productDto.getDescription());
            editedProduct.setName(productDto.getName());
            editedProduct.setPrice(productDto.getPrice());
            editedProduct.setProductCategory(productDto.getProductCategory());
            productRepository.save(editedProduct);
        }
    }


}
