package com.srinageswari.rezeptmonolith.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author smanickavasagam
 *     <p>Search Request used for filtering
 */
@Data
@NoArgsConstructor
public class SearchRequestDTO implements Serializable {

  private static final long serialVersionUID = 8514625832019794838L;

  private List<FilterRequestDTO> filters;

  private List<SortRequestDTO> sorts;

  private Integer page;

  private Integer size;

  public List<FilterRequestDTO> getFilters() {
    if (Objects.isNull(this.filters)) return new ArrayList<>();
    return this.filters;
  }

  public List<SortRequestDTO> getSorts() {
    if (Objects.isNull(this.sorts)) return new ArrayList<>();
    return this.sorts;
  }
}
