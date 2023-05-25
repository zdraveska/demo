package com.example.demo.dto;

import com.example.demo.domain.common.Address;
import com.example.demo.domain.common.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDto {

  Long id;

  String name;

  String description;

  List<ProductDto> products;

  List<Address> addresses;

  List<String> phoneNumbers;

  Image image;

}