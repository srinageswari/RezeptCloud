package com.srinageswari.rezeptmonolith.service.item;

import org.springframework.data.domain.Page;
import com.srinageswari.rezeptmonolith.dto.ItemDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemService {
  public ItemDTO findById(Long id);

  public Page<ItemDTO> findAll(SearchRequestDTO request);

  public ItemDTO create(ItemDTO request);

  public ItemDTO update(ItemDTO request);

  public void deleteById(Long id);

}
