package com.srinageswari.rezeptmonolith.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.srinageswari.rezeptmonolith.enums.Difficulty;
import com.srinageswari.rezeptmonolith.enums.Meal;

@Data
@NoArgsConstructor
public class CategoryDTO {

  @JsonProperty("categoryId")
  private Long categoryId;

  @NotBlank
  @JsonProperty("name")
  private String name;

  @JsonProperty("meal")
  @NotBlank
  private Meal meal;

  @JsonProperty("sidedish")
  private boolean sidedish;

  @JsonProperty("difficulty")
  private Difficulty difficulty;

  private int version;
}
