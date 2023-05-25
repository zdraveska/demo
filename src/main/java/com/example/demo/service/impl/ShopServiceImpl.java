package com.example.demo.service.impl;

import com.example.demo.domain.exceptions.ShopNotFoundException;
import com.example.demo.dto.ShopDto;
import com.example.demo.domain.shop.Product;
import com.example.demo.domain.shop.Shop;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopRepository;
import com.example.demo.service.ShopService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.example.demo.util.CsvUtil.parseProductsFromCsv;


@Slf4j
@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

  private final ShopRepository shopRepository;
  private final ProductRepository productRepository;
  private final ShopMapper shopMapper;

  @Transactional
  @Override
  public ShopDto uploadShop(MultipartFile file) {
    Shop shop = new Shop();
    shop = shopRepository.save(shop);
    List<Product> products = parseProductsFromCsv(file, shop);
    products = productRepository.saveAll(products);
    shop.setProducts(products);
    log.debug("Created shop {}", shop.getName());
    return shopMapper.toDto(shop);
  }

  @Override
  public Optional<Shop> getShop(Long id) {
    log.debug("Find Shop by id: " + id);
    return shopRepository.findById(id);
  }

  @Override
  public ShopDto findShopDtoById(Long id) {
    Shop shop = getShop(id)
        .orElseThrow(() -> new ShopNotFoundException(id));
    return shopMapper.toDto(shop);
  }

//  @Scheduled(cron = "0 0 8 * * MON")
//  protected void cleanUpShopEntity() {
//    log.debug("Deleting unused Shops");
//    ShopRepository.deleteByRestaurant(null);
//  }

}