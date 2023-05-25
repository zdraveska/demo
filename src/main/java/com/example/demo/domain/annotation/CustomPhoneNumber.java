package com.example.demo.domain.annotation;

import com.example.demo.domain.exceptions.InvalidInputException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class CustomPhoneNumber implements ConstraintValidator<ValidPhoneNumber, String> {

  @Override
  public void initialize(ValidPhoneNumber validEmail) {
  }

  @Override
  public boolean isValid(String phoneNumber,
      ConstraintValidatorContext cxt) {
    String mobileNumberWithCountryCallingCode = "^[+]3897\\d{7}";
    String mobileNumberRegex = "^07\\d{7}$";
    String homeNumberRegex = "^02\\d{7}$";
    boolean valid = phoneNumber != null && (phoneNumber.matches(mobileNumberWithCountryCallingCode)
        || phoneNumber.matches(mobileNumberRegex) || phoneNumber.matches(homeNumberRegex));
    if (!valid) {
      log.debug("Invalid phone number");
      throw new InvalidInputException();
    }
    return true;
  }

}