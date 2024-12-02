package com.srinageswari.rezeptmonolith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CulinaryStep {
  SOAK("soak"),
  TEMPER("temper"),
  FRY("fry"),
  FRY_GRIND("fry_grind"),
  GRIND("grind"),
  MARINATE("marinate"),
  PRESSURE_COOK("pressure_cook"),
  CHOP("chop"),
  GARNISH("garnish"),
  OTHERS("others");

  private final String label;
}
