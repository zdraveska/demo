package com.example.demo.dto;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.shop.ProductCategory;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    Long id;
    String name;
    String description;
    ProductCategory category;
    List<String> tags;
    Image image;
    Integer price;
    Integer quantity;
}