package com.example.demo.domain.shop;


import com.example.demo.domain.users.User;
import org.apache.commons.lang3.Range;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Post {

    @Id
    Long id;
    @ManyToOne
    User creator;
    String name;
    String description;
    LocalDate date;
    Range<Integer> priceRange;
    ProductCategory productCategory;
    ShopCategory shopCategory;
    @ElementCollection
    List<String> tags;
}
