package com.srinageswari.rezeptmonolith.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.dto.CategoryDTO;
import com.srinageswari.rezeptmonolith.dto.common.APIResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.service.category.ICategoryService;

import java.time.LocalDateTime;

/**
 * @author smanickavasagam
 */
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

  private final ICategoryService categoryService;

  /**
   * Fetches category by id
   *
   * @param id
   * @return A single category
   */
  @GetMapping("/category/{id}")
  public ResponseEntity<APIResponseDTO<CategoryDTO>> findById(@PathVariable long id) {
    final CategoryDTO response = categoryService.findById(id);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Fetches all categories based on the given category filter parameters
   *
   * @param request
   * @return Paginated category data
   */
  @GetMapping("/categories")
  public ResponseEntity<APIResponseDTO<Page<CategoryDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<CategoryDTO> response = categoryService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(
            LocalDateTime.now(), Constants.SUCCESS, response, response.getTotalElements()));
  }

  /**
   * Creates a new category
   *
   * @return id of the created category
   */
  @PostMapping("/category")
  public ResponseEntity<APIResponseDTO<CategoryDTO>> create(@RequestBody CategoryDTO request) {
    final CategoryDTO response = categoryService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Updates given category
   *
   * @return id of the updated category
   */
  @PutMapping("/category")
  public ResponseEntity<APIResponseDTO<CategoryDTO>> update(@RequestBody CategoryDTO request) {
    final CategoryDTO response = categoryService.update(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), Constants.SUCCESS, response, 1));
  }

  /**
   * Deletes category by id
   *
   * @return id of the deleted category
   */
  @DeleteMapping("/category/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    categoryService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
