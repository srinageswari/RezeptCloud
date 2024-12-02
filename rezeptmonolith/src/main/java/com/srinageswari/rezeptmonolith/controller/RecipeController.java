package com.srinageswari.rezeptmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import com.srinageswari.rezeptmonolith.dto.RecipeResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.APIResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.service.recipe.IRecipeService;
import com.srinageswari.rezeptmonolith.validator.ValidItem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author smanickavasagam
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RecipeController {

  private final IRecipeService recipeService;

  @GetMapping("/health")
  public String healthCheck() {
    return "UP";
  }

  /**
   * Fetches recipe by id
   *
   * @param id
   * @return A single recipe
   */
  @GetMapping("/recipe/{id}")
  public ResponseEntity<APIResponseDTO<RecipeResponseDTO>> findById(@PathVariable long id) {
    final RecipeResponseDTO response = recipeService.findById(id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Fetches all recipes based on the given recipe filter parameters
   *
   * @param request
   * @return Paginated recipe data
   */
  @GetMapping("/recipes")
  public ResponseEntity<APIResponseDTO<Page<RecipeResponseDTO>>> findAll(
      @RequestBody(required = false) SearchRequestDTO request) {
    if (request == null) {
      request = new SearchRequestDTO(); // Set default value
    }
    final Page<RecipeResponseDTO> response = recipeService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(
            LocalDateTime.now(), Constants.SUCCESS, response, response.getTotalElements()));
  }

  /**
   * Creates a new recipe
   *
   * @return id of the created recipe
   */
  @PostMapping("/recipe")
  public ResponseEntity<APIResponseDTO<RecipeResponseDTO>> create(
      @Valid @ValidItem(message = Constants.NOT_VALIDATED_ITEM) @RequestBody RecipeDTO request) {
    final RecipeResponseDTO response = recipeService.createOrUpdate(request);
    return ResponseEntity.ok(
            new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Creates bulk recipes
   *
   * @return size of the created recipe
   */
  @PostMapping("/bulkinsert/recipes")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> bulkInsert(
      @RequestBody List<RecipeDTO> recipeDTOS) {
    final List<RecipeResponseDTO> response = recipeService.bulkInsert(recipeDTOS);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new APIResponseDTO<>(
                LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }

  /**
   * Updates given recipe
   *
   * @return id of the updated recipe
   */
  @PutMapping("/recipe")
  public ResponseEntity<APIResponseDTO<RecipeResponseDTO>> update(
      @Valid @ValidItem(message = Constants.NOT_VALIDATED_ITEM) @RequestBody RecipeDTO request) {
    final RecipeResponseDTO response = recipeService.createOrUpdate(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Deletes recipe by id
   *
   * @return id of the deleted recipe
   */
  @DeleteMapping("/recipe/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    recipeService.deleteById(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/recipes/category/{category_id}")
  public ResponseEntity<APIResponseDTO<List<RecipeResponseDTO>>> search(
      @PathVariable long category_id) {
    final List<RecipeResponseDTO> response = recipeService.getRecipeByCategoryId(category_id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, response.size()));
  }
}
