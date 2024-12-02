package com.srinageswari.rezeptmonolith.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author smanickavasagam
 */
@Getter
@AllArgsConstructor
public enum Difficulty {
  EASY("Easy"),
  MODERATE("Moderate"),
  HARD("Hard"),
  NA("Not Applicable");

  private final String label;
}
