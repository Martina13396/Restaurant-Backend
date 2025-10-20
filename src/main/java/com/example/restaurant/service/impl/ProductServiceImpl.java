package com.example.restaurant.service.impl;

import com.example.restaurant.controller.vm.ProductResponseVm;
import com.example.restaurant.helper.SecurityUtils;
import com.example.restaurant.mapper.ProductMapper;
import com.example.restaurant.model.Category;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.ProductDetails;
import com.example.restaurant.repo.CategoryRepo;
import com.example.restaurant.repo.ProductDetailsRepo;
import com.example.restaurant.repo.ProductRepo;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.dto.ProductDto;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductDetailsRepo productDetailsRepo;
    ProductMapper productMapper;
    ProductRepo productRepo;
    CategoryRepo categoryRepo;


    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepo productRepo, CategoryRepo categoryRepo, ProductDetailsRepo productDetailsRepo) {
        this.productMapper = productMapper;
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.productDetailsRepo = productDetailsRepo;
    }

    @Override
    @Cacheable(value = "products", key = "#categoryId + '-' + #page + '-' + #size+ '-' + T(com.example.restaurant.helper.SecurityUtils).isAdmin()")
    public ProductResponseVm getProductsByCategoryId(Long categoryId, int page, int size) {
        boolean includeInactive = SecurityUtils.isAdmin();
        try {
            if (Objects.isNull(categoryId)) {
                throw new SystemException("id.not.null");
            }


            Pageable pageable = getPageable(page, size);
            Page<Product> result = includeInactive ? productRepo.findByCategoryIdIncludingInactive(categoryId, pageable)
                    : productRepo.findByCategoryIdByOrderAsc(categoryId, pageable);
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
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto saveProduct(ProductDto productDto) {
        if (Objects.nonNull(productDto.getId())) {

            throw new RuntimeException("id.must.null");
        }

        if (productDto.getCategoryId() == null) {
            throw new RuntimeException("category.id.notnull");
        }


        Category category = categoryRepo.findById(productDto.getCategoryId()).orElse(null);
        Product product = productMapper.toProduct(productDto);

        product.setCategory(category);
        if (productDto.getProductDetails() != null && productDto.getProductDetails().getDetails() != null) {
            ProductDetails details = new ProductDetails();

            details.setDetails(productDto.getProductDetails().getDetails());
            product.setProductDetails(details);
        }else{


            ProductDetails emptyDetails = new ProductDetails();
            emptyDetails.setDetails(null);
            product.setProductDetails(emptyDetails);
        }


            Product saved = productRepo.save(product);
            productDto.setId(saved.getId());


        return productMapper.toProductDto(saved);

    }

    @Override
    @CacheEvict(value = "products" , allEntries = true)
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
    @CacheEvict(value = "products" , allEntries = true)
    public ProductDto updateProduct(ProductDto productDto) {

        if (Objects.isNull(productDto.getId())) {
            throw new RuntimeException("id.not.null");
        }
        Product product = productRepo.findById(productDto.getId()).orElseThrow(()-> new RuntimeException("product.not.found"));

        productMapper.updateProductFromDto(productDto , product);

        if(productDto.getCategoryId() != null) {

            Category category = categoryRepo.findById(productDto.getCategoryId()).orElseThrow(()-> new RuntimeException("category.not.found"));
            product.setCategory(category);


        }else{
            product.setCategory(null);
        }


        Product updated = productRepo.save(product);
        return productMapper.toProductDto(updated);
    }

    @Override
    @CacheEvict(value = "products" , allEntries = true)
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
    @CacheEvict(value = "products" , allEntries = true)
    public ProductDto deactivate(Long id) {

        Product product = productRepo.findById(id).orElseThrow(()->new RuntimeException("product.not.found"));
        System.out.println("Before: " + product.isActive());
        product.setActive(false);
        Product updatedProduct = productRepo.saveAndFlush(product);
        Product check = productRepo.findById(id).orElseThrow();
        System.out.println("After: " + check.isActive());
      return productMapper.toProductDto(updatedProduct);
    }

    @Override
    @Cacheable(value = "products" , key = "#productsIds")
    public List<ProductDto> getAllProductsById(List<Long> productsIds ) {


      boolean includeInactive = SecurityUtils.isAdmin();


        List<Product> products = includeInactive?  productRepo.findAllById(productsIds):
                productRepo.findAllActiveById(productsIds);
        if (Objects.isNull(productsIds)) {
            throw new RuntimeException("ids.null");
        }
        return products.stream().map(product -> productMapper.toProductDto(product)).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "products" , allEntries = true)
    public void deleteProducts(List<Long> ids) {

       List<Product>products = productRepo.findAllById(ids);
       if (products.isEmpty()) {
           throw new RuntimeException("ids.not.found");
       }
       products.forEach(product->product.setActive(false));
       productRepo.saveAll(products);
    }

    @Override
    @Cacheable(value = "product" , key = "#keyword + '-' + #page + '-' + #size + '-' + T(com.example.restaurant.helper.SecurityUtils).isAdmin() ")
    public ProductResponseVm searchProducts(String keyword, int page, int size ) {
        boolean includeInactive = SecurityUtils.isAdmin();
        try {
            if (Objects.isNull(keyword)) {
                throw new SystemException("key.null");
            }


            Pageable pageable = getPageable(page, size);
            Page<Product> result = includeInactive? productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseIncludeInactive( keyword , pageable)
                    : productRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase( keyword, pageable);
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
    @Cacheable(value = "products" , key = "#page + '-' + #size + '-' + T(com.example.restaurant.helper.SecurityUtils).isAdmin()")
    public ProductResponseVm getAllProductsByIdAsc(int page, int size ) {
     boolean includeInactive = SecurityUtils.isAdmin();
        try {

            Pageable pageable = getPageable(page, size);


            Page<Product> result = includeInactive? productRepo.findAllIncludingInactive(pageable)
                    :productRepo.findAllByActiveTrueOrderByIdAsc(pageable);
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
    @Cacheable(value = "products" , key = "#categoryId + '-' + #keyword + '-' + #page + '-' + #size + '-' + T(com.example.restaurant.helper.SecurityUtils).isAdmin()")
    public ProductResponseVm searchByCategoryAndKeyword(Long categoryId, String keyword  , int page , int size ) {
        boolean includeInactive = SecurityUtils.isAdmin();
      try {
          if (Objects.isNull(keyword)) {
              throw new SystemException("key.null");
          }
          Pageable pageable = getPageable(page, size);

          Page<Product> result = includeInactive? productRepo.searchByCategoryAndKeywordIncludingInactive(keyword , categoryId , pageable)
                  :productRepo.searchByCategoryAndKeyword(keyword, categoryId, pageable);
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
    @Cacheable(value = "products" , key = "#id+ '-' + T(com.example.restaurant.helper.SecurityUtils).isAdmin()")
    public ProductDto getProductById(Long id ) {
       boolean includeInactive = SecurityUtils.isAdmin();

        Optional<Product> product = includeInactive?productRepo.findById(id)
             : productRepo.findActiveById(id);
     if (!product.isPresent()) {
         throw new RuntimeException("product.not.found");
     }
     return productMapper.toProductDto(product.get());

    }

    @Override
    public Product getPersistentProductById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("id.null");

        }
        Optional<Product> product = productRepo.findActiveById(id);
        if (!product.isPresent()) {
            throw new RuntimeException("product.not.found");
        }
      return product.get();
    }

    @Override
    public List<Product> getAllPersistentProductsByIds(List<Long> ids) {
       if(ids == null || ids.isEmpty()){
           throw new RuntimeException("ids.null");
       }
       List<Product> products = productRepo.findAllActiveById(ids);
       if(products.size()!= ids.size()){
           throw new RuntimeException("products.not.available");
       }
       return products;
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepo.findAll();
        if (products.isEmpty()) {
            return new ArrayList<>();
        }
        return products .stream().map(ProductMapper.PRODUCT_MAPPER::toProductDto).toList();
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
