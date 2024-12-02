package com.srinageswari.rezeptmonolith.service.recipe;

import org.springframework.data.domain.Page;
import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import com.srinageswari.rezeptmonolith.dto.RecipeResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;

import java.util.List;

/**
 * @author smanickavasagam
 */
public interface IRecipeService {
  RecipeResponseDTO findById(Long id);

  Page<RecipeResponseDTO> findAll(SearchRequestDTO request);

  RecipeResponseDTO createOrUpdate(RecipeDTO request);

  List<RecipeResponseDTO> bulkInsert(List<RecipeDTO> recipeDTOList);

  RecipeResponseDTO update(RecipeDTO request);

  List<RecipeResponseDTO> getRecipeByCategoryId(Long id);

  void deleteById(Long id);
}
