package com.example.demo.dto.input.shop;

import com.example.demo.domain.annotation.ValidPhoneNumber;
import com.example.demo.domain.common.Address;
import com.example.demo.domain.shop.ShopCategory;
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
public class CreateShopDto {

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 50)
    String name;

    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 255)
    String description;

    @NotNull
    ShopCategory category;

    @NotEmpty
    @Size(min = 1)
    List<Address> addresses;

    @NotBlank
    @NotEmpty
    @ValidPhoneNumber
    @Size(min = 1, max = 50)
    String phoneNumber;



}