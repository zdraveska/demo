package com.example.demo.domain.shop;

import com.example.demo.domain.common.Address;
import com.example.demo.domain.common.Image;
import com.example.demo.domain.users.User;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    Long id;
    String name;
    String description;
    ShopCategory shopCategory;
    @OneToMany
    List<Address> addresses;
    //https://stackoverflow.com/a/57241586
    @ElementCollection
    @Column(name = "phoneNumbers")
    List<String> phoneNumbers;
    @ManyToMany
    List<User> employees;
    @OneToMany
    List<Product> products;
    @OneToOne
    Image image;

}
