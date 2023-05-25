package com.example.demo.domain.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account_request")
public class AccountRequest {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "name")
  String name;

  @Column(name = "surname")
  String surname;

  @Column(name = "email")
  String email;

  @Column(name = "email_confirmed")
  Boolean emailConfirmed;

  @Column(name = "created_date")
  LocalDate createdDate;
}