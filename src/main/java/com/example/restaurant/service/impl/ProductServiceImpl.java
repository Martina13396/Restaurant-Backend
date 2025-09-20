package com.example.restaurant.service.impl;

import com.example.restaurant.controller.vm.ProductResponseVm;
import com.example.restaurant.mapper.ProductMapper;
import com.example.restaurant.model.Product;
import com.example.restaurant.repo.ProductRepo;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.dto.ProductDto;
import jakarta.transaction.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    ProductMapper productMapper;
    ProductRepo productRepo;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepo productRepo) {
        this.productMapper = productMapper;
        this.productRepo = productRepo;
    }

    @Override
    public ProductResponseVm getProductsByCategoryId(Long categoryId, int page, int size) {
        try {
            if (Objects.isNull(categoryId)) {
                throw new SystemException("id.not.null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findByCategoryIdByOrderAsc(categoryId, pageable);
            if (result.isEmpty()) {
                throw new SystemException("product.category.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {

            throw new RuntimeException(e.getMessage());
        }


    }


    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        if (Objects.nonNull(productDto.getId())) {

            throw new RuntimeException("id.not.null");
        }
        Product product = productMapper.toProduct(productDto);
        Product saved = productRepo.save(product);
        productDto.setId(product.getId());

        return productMapper.toProductDto(saved);

    }

    @Override
    public List<ProductDto> saveProducts(List<ProductDto> productDtos) {

        for (ProductDto productDto : productDtos) {
            if (Objects.nonNull(productDto.getId())) {
                throw new RuntimeException("id.must.null");
            }
        }
        List<Product> products = productMapper.toProductList(productDtos);
        List<Product> savedProducts = productRepo.saveAll(products);


        return savedProducts.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        if (Objects.isNull(productDto.getId())) {
            throw new RuntimeException("id.not.null");
        }

        Product product = productMapper.toProduct(productDto);
        Product updated = productRepo.save(product);
        return productMapper.toProductDto(updated);

    }

    @Override
    public List<ProductDto> updateProducts(List<ProductDto> productDtos) {
        for (ProductDto productDto : productDtos) {
            if (Objects.isNull(productDto.getId())) {
                throw new RuntimeException("id.not.null");
            }
        }
        List<Product> products = productMapper.toProductList(productDtos);
        List<Product> updatedProducts = productRepo.saveAll(products);
        return updatedProducts.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());

    }


    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public List<ProductDto> getAllProductsById(List<Long> productsIds) {
        List<Product> products = productRepo.findAllById(productsIds);
        if (Objects.isNull(productsIds)) {
            throw new RuntimeException("ids.null");
        }
        return products.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    public void deleteProducts(List<Long> ids) {

        productRepo.deleteAllById(ids);
    }

    @Override
    public ProductResponseVm searchProducts(String keyword, int page, int size) {
        try {
            if (Objects.isNull(keyword)) {
                throw new SystemException("key.null");
            }
            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                    result.getTotalElements()
            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductResponseVm getAllProductsByIdAsc(int page, int size) {

        try {

            Pageable pageable = getPageable(page, size);
            Page<Product> result = productRepo.findAllByOrderByIdAsc(pageable);
            if (result.isEmpty()) {
                throw new SystemException("products.not.found");
            }
            return new ProductResponseVm(
                    result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList().stream().toList(),
                    result.getTotalElements()

            );
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    public ProductResponseVm searchByCategoryAndKeyword(Long categoryId, String keyword  , int page , int size) {
      try {
          if (Objects.isNull(keyword)) {
              throw new SystemException("key.null");
          }
          Pageable pageable = getPageable(page, size);
          Page<Product> result = productRepo.searchByCategoryAndKeyword(keyword, categoryId, pageable);
          if (result.isEmpty()) {
              throw new SystemException("products.not.found");
          }
          return new ProductResponseVm(
                  result.getContent().stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList(),
                  result.getTotalElements()
          );
      }catch (SystemException e) {
          throw new RuntimeException(e.getMessage());
      }
    }

    @Override
    public ProductDto getProductById(Long id) {

     Optional<Product> product = productRepo.findById(id);
     if (!product.isPresent()) {
         throw new RuntimeException("product.not.found");
     }
     return productMapper.toProductDto(product.get());

    }

    private Pageable getPageable(int page, int size) {
        try {
            if (page< 1){
                throw new SystemException("error.min.one.page");
            }
            return PageRequest.of(page -1, size);
        } catch (SystemException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
