package com.srinageswari.rezeptmonolith.common;

import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import com.srinageswari.rezeptmonolith.service.recipeitem.RecipeItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.srinageswari.rezeptmonolith.dto.RecipeResponseDTO;
import com.srinageswari.rezeptmonolith.enums.CulinaryStep;
import com.srinageswari.rezeptmonolith.model.RecipeEntity;
import com.srinageswari.rezeptmonolith.model.RecipeItemEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j(topic = "RecipeUtil")
@RequiredArgsConstructor
@Component
public class RecipeUtil {

  public static Long uuidToLong(UUID uuid) {
    long mostSigBits = uuid.getMostSignificantBits();
    long leastSigBits = uuid.getLeastSignificantBits();

    // Combine the most and least significant bits to create a long value
    return (mostSigBits & Long.MAX_VALUE) ^ (leastSigBits & Long.MAX_VALUE);
  }

  public static RecipeResponseDTO getRecipeResponseDTO(RecipeEntity recipe) {

    RecipeResponseDTO recipeResponseDTO = new RecipeResponseDTO();
    recipeResponseDTO.setRecipeId(recipe.getId());
    recipeResponseDTO.setTitle(recipe.getTitle());
    recipeResponseDTO.setReference(recipe.getReference());
    recipeResponseDTO.setPrepTime(recipe.getPrepTime());
    recipeResponseDTO.setCookTime(recipe.getCookTime());
    recipeResponseDTO.setInstructions(recipe.getInstructions());
    recipeResponseDTO.setHealthLabel(
            recipe.getHealthLabel() != null ? recipe.getHealthLabel().getLabel() : null);
    recipeResponseDTO.setCuisine(recipe.getCuisine().getLabel());
    recipeResponseDTO.setCategory(recipe.getCategory().getName());
    recipeResponseDTO.setMeal(recipe.getCategory().getMeal().getLabel());
    recipeResponseDTO.setDifficulty(recipe.getCategory().getDifficulty().getLabel());
    recipeResponseDTO.setSideDish(recipe.getCategory().isSidedish());
    recipeResponseDTO.setNotes(recipe.getNotes());
    recipeResponseDTO.setServings(recipe.getServings());
    Map<CulinaryStep, List<RecipeItemEntity>> groupedByCulinaryStep =
            recipe.getRecipeItems().stream()
                    .filter(recipeItem -> recipeItem.getCulinaryStep() != null)  // Filter out null CulinaryStep
                    .collect(Collectors.groupingBy(RecipeItemEntity::getCulinaryStep));

    if(!groupedByCulinaryStep.isEmpty()){
    Map<String, Map<String, String>> ingredients = new HashMap<>();

    groupedByCulinaryStep.forEach(
            (culinaryStep, recipeItems) -> {
              Map<String, String> ingredientQtyMap = new HashMap<>();
              recipeItems.forEach(
                      recipeItem -> {
                        ingredientQtyMap.put(
                                recipeItem.getItem().getName(),
                                formatQty(recipeItem.getRequiredQty())
                                        + recipeItem.getRecipeUnit().getLabel());
                      });
              ingredients.put(culinaryStep.getLabel(), ingredientQtyMap);
            });
    recipeResponseDTO.setIngredientsCulinaryStepMap(ingredients);
  }
    return recipeResponseDTO;
  }

  public static String formatQty(BigDecimal qty) {
    return (qty.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
        ? String.valueOf(qty.intValue())
        : qty.stripTrailingZeros().toPlainString();
  }

}
