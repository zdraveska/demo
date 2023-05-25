package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ShopDto;
import com.example.demo.dto.input.shop.CreateProductDto;
import com.example.demo.dto.input.shop.UpdateProductDto;

public interface ProductService {

  ProductDto createProduct(CreateProductDto createProductDto, Long shopId);

  void deleteShopProduct(Long id);

  ShopDto updateProduct(UpdateProductDto updateProductDto, Long ProductId);
}