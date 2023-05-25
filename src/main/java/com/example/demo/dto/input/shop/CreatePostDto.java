package com.example.demo.dto.input.shop;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.shop.ProductCategory;
import com.example.demo.domain.shop.ShopCategory;
import com.example.demo.dto.BuyerDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.Range;

import jakarta.persistence.ElementCollection;
import java.time.LocalDate;
import java.util.List;

public class CreatePostDto {

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 50)
    String name;

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 255)
    String description;

    @NotNull
    @NotEmpty
    Range<Integer> priceRange;

    ProductCategory productCategory;

    ShopCategory shopCategory;

    @NotEmpty
    @Size(min = 1)
    List<String> tags;

}
