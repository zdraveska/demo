package com.example.demo.mapper;

import com.example.demo.domain.shop.Product;
import com.example.demo.domain.shop.Shop;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.input.shop.CreateProductDto;
import com.example.demo.dto.input.shop.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "shop", source = "shop")
    @Mapping(target = "name", source = "createProductDto.name")
    @Mapping(target = "description", source = "createProductDto.description")
    @Mapping(target = "image", source = "createProductDto.image")
    @Mapping(target = "id", ignore = true)
    Product createDtoToEntity(CreateProductDto createProductDto, Shop shop);

    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product updateDtoToEntity(@MappingTarget Product product, UpdateProductDto updateProductDto);
}
