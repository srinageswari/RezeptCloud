package com.srinageswari.rezeptmonolith.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.srinageswari.rezeptmonolith.enums.Sort;

import java.io.Serializable;

/**
 * @author smanickavasagam
 *     <p>Sort Request used for filtering
 */
@Data
@NoArgsConstructor
public class SortRequestDTO implements Serializable {

  private static final long serialVersionUID = 3194362295851723069L;

  private String key;

  private Sort direction;
}
