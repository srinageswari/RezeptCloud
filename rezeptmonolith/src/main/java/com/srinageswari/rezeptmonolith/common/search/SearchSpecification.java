package com.srinageswari.rezeptmonolith.common.search;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.srinageswari.rezeptmonolith.dto.common.FilterRequestDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.dto.common.SortRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author smanickavasagam
 *     <p>Search specifications used for filtering
 */
@Slf4j
@AllArgsConstructor
public class SearchSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = -9153865343320750644L;

  private final transient SearchRequestDTO request;

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
    Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);

    for (FilterRequestDTO filter : this.request.getFilters()) {
      log.info(
          "Filter: {} {} {}", filter.getKey(), filter.getOperator().toString(), filter.getValue());
      predicate = filter.getOperator().build(root, cb, filter, predicate);
    }

    List<Order> orders = new ArrayList<>();
    for (SortRequestDTO sort : this.request.getSorts()) {
      orders.add(sort.getDirection().build(root, cb, sort));
    }

    query.orderBy(orders);
    return predicate;
  }

  public static Pageable getPageable(Integer page, Integer size) {
    return PageRequest.of(
        Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 100));
  }
}
