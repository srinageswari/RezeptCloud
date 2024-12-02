package com.srinageswari.rezeptmonolith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Cuisine {
  EUROPEAN("European"),
  INDIAN("Indian");
  private final String label;
}
