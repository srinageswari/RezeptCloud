package com.srinageswari.rezeptmonolith.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.srinageswari.rezeptmonolith.dto.ItemtypeDTO;
import com.srinageswari.rezeptmonolith.dto.common.APIResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.service.itemtype.IItemtypeService;

import java.time.LocalDateTime;

import static com.srinageswari.rezeptmonolith.common.Constants.SUCCESS;

/**
 * @author smanickavasagam
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ItemtypeController {

  private final IItemtypeService itemtypeService;

  /**
   * Fetches itemtype by id
   *
   * @param id
   * @return A single itemtype
   */
  @GetMapping("/itemtype/{id}")
  public ResponseEntity<APIResponseDTO<ItemtypeDTO>> findById(@PathVariable long id) {
    final ItemtypeDTO response = itemtypeService.findById(id);
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response, 1));
  }

  /**
   * Fetches all itemtypes based on the given filter parameters
   *
   * @param request
   * @return Paginated itemtype data
   */
  @GetMapping("/itemtypes")
  public ResponseEntity<APIResponseDTO<Page<ItemtypeDTO>>> findAll(
      @RequestBody SearchRequestDTO request) {
    final Page<ItemtypeDTO> response = itemtypeService.findAll(request);
    return ResponseEntity.ok(
        new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response, response.getTotalElements()));
  }

  /**
   * Creates a new itemtype
   *
   * @return id of the created itemtype
   */
  @PostMapping("/itemtype")
  public ResponseEntity<APIResponseDTO<ItemtypeDTO>> create(
      @Valid @RequestBody ItemtypeDTO request) {
    final ItemtypeDTO response = itemtypeService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response, 1));
  }

  /**
   * Updates given itemtype
   *
   * @return id of the updated itemtype
   */
  @PutMapping("/itemtype")
  public ResponseEntity<APIResponseDTO<ItemtypeDTO>> update(
      @Valid @RequestBody ItemtypeDTO request) {
    final ItemtypeDTO response = itemtypeService.update(request);
    return ResponseEntity.ok(new APIResponseDTO<>(LocalDateTime.now(), SUCCESS, response, 1));
  }

  /**
   * Deletes itemtype by id
   *
   * @return id of the deleted itemtype
   */
  @DeleteMapping("/itemtype/{id}")
  public ResponseEntity<APIResponseDTO<Void>> deleteById(@PathVariable long id) {
    itemtypeService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
