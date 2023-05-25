package com.example.demo.util;


import com.example.demo.domain.common.EmailType;
import com.example.demo.dto.EmailDto;

public final class EmailUtil {

  public static EmailDto generateEmail(String emailAddress, String name, String surname, EmailType type,
                                       String url) {
    return EmailDto.builder()
        .to(emailAddress)
        .subject(type.getSubject())
        .body(type.getBody())
        .toName(name)
        .toSurname(surname)
        .url(url)
        .build();
  }

}