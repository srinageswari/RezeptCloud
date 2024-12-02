package com.srinageswari.rezeptmonolith.enums;

import com.srinageswari.rezeptmonolith.common.Constants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import com.srinageswari.rezeptmonolith.dto.common.FilterRequestDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author smanickavasagam
 *     <p>Operator definitions used for filtering
 */
@Slf4j
public enum Operator {
  EQUAL {
    public <T> Predicate build(
        Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate) {
      Object value = request.getFilterType().parse(request.getValue().toString());
      Expression<?> key = root.get(request.getKey());
      return cb.and(cb.equal(key, value), predicate);
    }
  },

  NOT_EQUAL {
    public <T> Predicate build(
        Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate) {
      Object value = request.getFilterType().parse(request.getValue().toString());
      Expression<?> key = root.get(request.getKey());
      return cb.and(cb.notEqual(key, value), predicate);
    }
  },

  LIKE {
    public <T> Predicate build(
        Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate) {
      Expression<String> key = root.get(request.getKey());
      return cb.and(
          cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"),
          predicate);
    }
  },

  IN {
    public <T> Predicate build(
        Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate) {
      List<Object> values = request.getValues();
      CriteriaBuilder.In<Object> inClause = cb.in(root.get(request.getKey()));
      for (Object value : values) {
        inClause.value(request.getFilterType().parse(value.toString()));
      }
      return cb.and(inClause, predicate);
    }
  },

  BETWEEN {
    public <T> Predicate build(
        Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate) {
      Object value = request.getFilterType().parse(request.getValue().toString());
      Object valueTo = request.getFilterType().parse(request.getValueTo().toString());
      if (request.getFilterType() == FilterType.DATE) {
        LocalDateTime startDate = (LocalDateTime) value;
        LocalDateTime endDate = (LocalDateTime) valueTo;
        Expression<LocalDateTime> key = root.get(request.getKey());
        return cb.and(
            cb.and(cb.greaterThanOrEqualTo(key, startDate), cb.lessThanOrEqualTo(key, endDate)),
            predicate);
      }

      if (request.getFilterType() != FilterType.CHAR
          && request.getFilterType() != FilterType.BOOLEAN) {
        Number start = (Number) value;
        Number end = (Number) valueTo;
        Expression<Number> key = root.get(request.getKey());
        return cb.and(cb.and(cb.ge(key, start), cb.le(key, end)), predicate);
      }

      log.warn(Constants.FIELD_TYPE_ERROR, request.getFilterType());
      return predicate;
    }
  };

  public abstract <T> Predicate build(
      Root<T> root, CriteriaBuilder cb, FilterRequestDTO request, Predicate predicate);
}
