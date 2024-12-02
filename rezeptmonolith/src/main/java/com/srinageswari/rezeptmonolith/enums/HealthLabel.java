package com.srinageswari.rezeptmonolith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum HealthLabel {
  DEFAULT("Default"),
  EGG_FREE("Egg-free"),
  GLUTEN_FREE("Gluten-free"),
  DIABETIC("Diabetic"),
  NO_SUGAR("No-sugar"),
  RED_MEAT_FREE("Red-meat-free"),
  VEGETARIAN("Vegetarian"),
  WHEAT_FREE("Wheat-free");

  private final String label;
}
