package com.example.demo.dto.input.shop;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.shop.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductDto {

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 50)
    String name;

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 50)
    String description;

    @NotNull
    ProductCategory category;

    @NotNull
    Image image;

    @NotNull
    Integer price;

    @NotNull
    Integer quantity;
}