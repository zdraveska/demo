package com.example.demo.dto;


import com.example.demo.domain.shop.ProductCategory;
import com.example.demo.domain.shop.ShopCategory;
import org.apache.commons.lang3.Range;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;

public class PostDto {

    Long id;
    BuyerDto creator;
    String name;
    String description;
    LocalDate date;
    Range<Integer> priceRange;
    ProductCategory productCategory;
    ShopCategory shopCategory;
    @ElementCollection
    List<String> tags;
}
