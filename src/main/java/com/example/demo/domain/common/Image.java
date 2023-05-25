package com.example.demo.domain.common;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "images")
public class Image {

    @Id
    @Column(unique = true, name = "id")
    Long id;

    @Column(name = "image")
    byte[] image;

    @Column(name = "image_content_type")
    String imageContentType;

    @Column(name = "image_name")
    String imageName;
}
