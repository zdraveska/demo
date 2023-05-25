package com.example.demo.service;

import com.example.demo.domain.shop.Shop;
import com.example.demo.dto.ShopDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ShopService {

  ShopDto uploadShop (MultipartFile file);

  Optional<Shop> getShop(Long id);

  ShopDto findShopDtoById(Long id);

}