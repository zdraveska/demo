package com.example.demo.domain.shop;

import com.example.demo.domain.common.Image;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    Long id;
    String name;
    String description;
    ProductCategory category;
    @ElementCollection // https://stackoverflow.com/a/57241586
    List<String> tags;
    @ManyToOne
    Shop shop;
    @OneToOne
    Image image;
    Integer price;
    Integer quantity;

}
