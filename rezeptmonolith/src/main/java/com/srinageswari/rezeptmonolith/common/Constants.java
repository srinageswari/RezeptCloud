package com.srinageswari.rezeptmonolith.common;

import org.springframework.beans.factory.annotation.Value;

import java.time.format.DateTimeFormatter;

/**
 * @author smanickavasagam
 *     <p>Constant variables used in the project
 */
public final class Constants {

  private Constants() {}


  @Value("${spring.jackson.date-format}")
  private static String dateFormat;

  public static final String SUCCESS = "Success";
  public static final String FIELD_PARSE_ERROR = "Failed parse field type DATE {}";
  public static final String FIELD_TYPE_ERROR = "Can not use between for {} field type";
  public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(dateFormat);
  public static final String TRACE = "trace";
  public static final String VALIDATION_ERROR =
      "Validation error. Check 'errors' field for details";
  public static final String METHOD_ARGUMENT_NOT_VALID = "MethodArgumentNotValid exception";
  public static final String NOT_FOUND = "Requested element is not found";
  public static final String NOT_FOUND_RECORD = "Not found any record";
  public static final String NOT_FOUND_ITEM = "Requested item is not found";
  public static final String NOT_FOUND_RECIPE = "Requested recipe is not found";
  public static final String ALREADY_EXISTS = "Requested element already exists";
  public static final String ALREADY_EXISTS_ITEM = "Requested item already exists (Item: %d)";
  public static final String NOT_VALIDATED_ELEMENT = "Failed to validate the input";
  public static final String NOT_VALIDATED_ITEM = "There are duplicate items for the given recipe";
}
