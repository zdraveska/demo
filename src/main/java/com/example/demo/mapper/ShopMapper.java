package com.example.demo.mapper;

import com.example.demo.domain.shop.Shop;
import com.example.demo.dto.ShopDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {

  ShopDto toDto(Shop Shop);

}