package com.example.demo.util;

import com.example.demo.domain.exceptions.InvalidInputException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class ValidationUtil {

  public static void checkIfAlphabetic(String... inputs) {
    if (!Arrays.stream(inputs).allMatch(StringUtils::isAlpha)) {
      throw new InvalidInputException();
    }
  }
}
