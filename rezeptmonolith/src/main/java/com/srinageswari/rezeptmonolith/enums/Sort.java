package com.srinageswari.rezeptmonolith.enums;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import com.srinageswari.rezeptmonolith.dto.common.SortRequestDTO;

/**
 * @author smanickavasagam
 *     <p>Sort directions used for filtering
 */
public enum Sort {
  ASC {
    public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request) {
      return cb.asc(root.get(request.getKey()));
    }
  },
  DESC {
    public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request) {
      return cb.desc(root.get(request.getKey()));
    }
  };

  public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request);
}
