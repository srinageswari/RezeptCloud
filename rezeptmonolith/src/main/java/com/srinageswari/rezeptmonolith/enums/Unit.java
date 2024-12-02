package com.srinageswari.rezeptmonolith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Unit {
  GRAM("g"),
  MILLILITER("ml"),
  LITRE("l"),
  TEASPOON("tsp"),
  TABLESPOON("tbsp"),
  CUP("cup"),
  COUNT("count"),
  PACK("pack"),
  FEW("few"),
  NA("not applicable");

  private final String label;
}
