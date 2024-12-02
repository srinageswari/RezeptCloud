package com.srinageswari.rezeptmonolith.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RecipeResponseDTO {
  private Long recipeId;
  private String title;
  private String reference;
  private Integer prepTime;
  private Integer cookTime;
  private String instructions;
  private String healthLabel;
  private String cuisine;
  private String category;
  private String meal;
  private String difficulty;
  private Boolean sideDish;
  private String notes;
  private Integer servings;

  @JsonProperty("ingredients")
  Map<String, Map<String, String>> ingredientsCulinaryStepMap = new HashMap<>();
}
