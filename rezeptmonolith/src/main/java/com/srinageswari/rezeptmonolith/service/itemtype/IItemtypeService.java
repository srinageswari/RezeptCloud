package com.srinageswari.rezeptmonolith.service.itemtype;

import org.springframework.data.domain.Page;
import com.srinageswari.rezeptmonolith.dto.ItemtypeDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;

/**
 * @author smanickavasagam
 */
public interface IItemtypeService {

  public ItemtypeDTO findById(Long id);

  public Page<ItemtypeDTO> findAll(SearchRequestDTO request);

  public ItemtypeDTO create(ItemtypeDTO request);

  public ItemtypeDTO update(ItemtypeDTO request);

  public void deleteById(Long id);
}
