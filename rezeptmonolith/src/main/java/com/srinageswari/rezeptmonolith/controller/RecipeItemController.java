package com.srinageswari.rezeptmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.srinageswari.rezeptmonolith.dto.RecipeItemDTO;
import com.srinageswari.rezeptmonolith.dto.common.APIResponseDTO;
import com.srinageswari.rezeptmonolith.service.recipeitem.IRecipeItemService;

import java.time.LocalDateTime;

import static com.srinageswari.rezeptmonolith.common.Constants.SUCCESS;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeItemController {

  private final IRecipeItemService recipeItemService;

  /**
   * Adds item to a recipe
   *
   * @return id of the recipe to which item is added
   */
  @PostMapping("/recipeItems")
  public ResponseEntity<APIResponseDTO<RecipeItemDTO>> addItemToRecipe(
      @Valid @RequestBody RecipeItemDTO request) {
    final RecipeItemDTO response = recipeItemService.addItemToRecipe(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response, 1));
  }

  /**
   * Removes item from a recipe by recipeId and itemId
   *
   * @return id of the recipe from which item is removed
   */
  @DeleteMapping("/recipeItems/recipes/{recipeId}/items/{itemId}")
  public ResponseEntity<APIResponseDTO<Void>> removeItemFromRecipe(
      @PathVariable long recipeId, @PathVariable long itemId) {
    recipeItemService.removeItemFromRecipe(recipeId, itemId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
