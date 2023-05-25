package com.example.demo.dto;

import com.example.demo.domain.common.Image;
import com.example.demo.domain.shop.Product;

import java.util.List;

public class BuyerDto {

    Long id;

    String name;

    String surname;

    String email;

    Image image;

    List<Product> boughtProducts;

    List<Product> favouritesProducts;
}
