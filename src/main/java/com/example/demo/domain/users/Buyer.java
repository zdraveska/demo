package com.example.demo.domain.users;

import com.example.demo.domain.shop.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Buyer extends User{

    @ManyToMany
    List<Product> boughtProducts;
    @ManyToMany
    List<Product> favouritesProducts;
}
