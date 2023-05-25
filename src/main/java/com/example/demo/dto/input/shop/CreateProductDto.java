package com.example.demo.dto.input.shop;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.shop.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductDto {

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 50)
    String name;

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 255)
    String description;

    @NotNull
    ProductCategory categories;

    @NotEmpty
    @Size(min = 1)
    List<String> tags;

    @NotNull
    Image image;

    @NotNull
    Integer price;

    @NotNull
    Integer quantity;

}