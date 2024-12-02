package com.srinageswari.rezeptmonolith.validator;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author smanickavasagam
 *     <p>Class for listing validation error messages
 */
public class ValidationErrorResponse {

  private List<Violation> violations;

  @Data
  private static class Violation {
    private final String fieldName;
    private final String message;
  }

  /**
   * Adds validation error for handling unprocessable entity
   *
   * @param field
   * @param message
   */
  public void addValidationError(String field, String message) {
    if (Objects.isNull(violations)) {
      violations = new ArrayList<>();
    }
    violations.add(new Violation(field, message));
  }
}
