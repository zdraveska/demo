package com.example.demo.dto.input.shop;

import com.example.demo.domain.annotation.ValidPhoneNumber;
import com.example.demo.domain.shop.ProductCategory;
import com.example.demo.domain.shop.ShopCategory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateShopDto {

  @NotBlank
  @NotEmpty
  @Size(min = 1, max = 50)
  String name;

  @NotBlank
  @NotEmpty
  @Size(min = 1, max = 255)
  String address;

  @NotBlank
  @NotEmpty
  @ValidPhoneNumber
  @Size(min = 1, max = 50)
  String phoneNumber;

  @NotNull
  ShopCategory category;

}