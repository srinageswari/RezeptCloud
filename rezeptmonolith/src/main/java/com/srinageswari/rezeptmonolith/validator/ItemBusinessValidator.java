package com.srinageswari.rezeptmonolith.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.srinageswari.rezeptmonolith.dto.RecipeDTO;

import java.util.HashSet;
import java.util.List;

/**
 * @author smanickavasagam
 *     <p>Validates the items in the RecipeRequest. If the request contains duplicate items returns
 *     error
 */
@Slf4j(topic = "ItemBusinessValidator")
@RequiredArgsConstructor
public class ItemBusinessValidator implements ConstraintValidator<ValidItem, RecipeDTO> {

  @Override
  public void initialize(ValidItem constraintAnnotation) {}

  @Override
  public boolean isValid(RecipeDTO request, ConstraintValidatorContext context) {
    return areAllUnique(
        request.getRecipeItems().stream()
            .map(recipeItemDTO -> recipeItemDTO.getItem().getName())
            .toList());
  }

  /**
   * Checks if all the given list values are unique or not
   *
   * @param list
   * @param <T>
   * @return
   */
  public <T> boolean areAllUnique(List<T> list) {
    return list.stream().allMatch(new HashSet<>()::add);
  }
}
