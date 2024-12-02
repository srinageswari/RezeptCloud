package com.srinageswari.rezeptmonolith.dto.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.srinageswari.rezeptmonolith.enums.FilterType;
import com.srinageswari.rezeptmonolith.enums.Operator;

import java.io.Serializable;
import java.util.List;

/**
 * @author smanickavasagam
 *     <p>Filter Request used for filtering
 */
@Data
@NoArgsConstructor
public class FilterRequestDTO implements Serializable {

  private static final long serialVersionUID = 6293344849078612450L;

  private String key;

  private Operator operator;

  private FilterType filterType;

  private transient Object value;

  private transient Object valueTo;

  private transient List<Object> values;
}
