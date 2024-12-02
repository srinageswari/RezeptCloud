package com.srinageswari.rezeptmonolith.service.category;

import org.springframework.data.domain.Page;
import com.srinageswari.rezeptmonolith.dto.CategoryDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface ICategoryService {

  public CategoryDTO findById(Long id);

  public Page<CategoryDTO> findAll(SearchRequestDTO request);

  public CategoryDTO create(CategoryDTO request);

  public CategoryDTO update(CategoryDTO request);

  public void deleteById(Long id);
}
