package com.example.demo.service.impl;

import com.example.demo.domain.exceptions.ProductNotFoundException;
import com.example.demo.domain.exceptions.ShopNotFoundException;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ShopDto;
import com.example.demo.dto.input.shop.CreateProductDto;
import com.example.demo.dto.input.shop.UpdateProductDto;
import com.example.demo.domain.shop.Product;
import com.example.demo.domain.shop.Shop;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ShopService shopService;
  private final ShopMapper shopMapper;

  @Override
  public ProductDto createProduct(CreateProductDto createProductDto, Long shopId) {
    Shop Shop = shopService.getShop(shopId).
        orElseThrow(() -> new ShopNotFoundException(shopId));
    Product product = productMapper.createDtoToEntity(createProductDto, Shop);

    productRepository.save(product);
    log.debug("Product {} created", product);
    return productMapper.toDto(product);
  }

  @Override
  public void deleteShopProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

    productRepository.delete(product);
    log.debug("Product has been deleted");
  }

  @Override
  public ShopDto updateProduct(UpdateProductDto updateProductDto, Long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
    product = productMapper.updateDtoToEntity(product, updateProductDto);
    productRepository.save(product);
    log.debug("Product {} modified", product);
    return shopMapper.toDto(product.getShop());
  }

}