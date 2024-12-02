package com.srinageswari.rezeptmonolith.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.srinageswari.rezeptmonolith.enums.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.srinageswari.rezeptmonolith.enums.CulinaryStep;

import java.math.BigDecimal;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for RecipeItem
 */
@Data
@NoArgsConstructor
public class RecipeItemDTO {
  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
  private RecipeDTO recipe;

  @NotNull private ItemDTO item;

  @JsonProperty("requiredQty")
  @NotNull
  private BigDecimal requiredQty;

  @JsonProperty("culinaryStep")
  private CulinaryStep culinaryStep;

  @JsonProperty("recipeUnit")
  @NotNull
  private Unit recipeUnit;
}
