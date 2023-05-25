package com.example.demo.dto.input.shop;

import com.example.demo.domain.shop.ProductCategory;
import com.example.demo.domain.shop.ShopCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.Range;

import java.util.List;

public class UpdatePostDto {

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

    @NotNull
    ProductCategory productCategory;

    @NotNull
    ShopCategory shopCategory;

    @NotEmpty
    @Size(min = 1)
    List<String> tags;

}
