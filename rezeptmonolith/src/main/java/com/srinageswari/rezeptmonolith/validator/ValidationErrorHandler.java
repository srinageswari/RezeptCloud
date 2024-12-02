package com.srinageswari.rezeptmonolith.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.srinageswari.rezeptmonolith.common.Constants;

/**
 * @author smanickavasagam
 *     <p>Error handler class for handling validation errors
 */
@Slf4j(topic = "ValidationErrorHandler")
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationErrorHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onConstraintValidationException(ConstraintViolationException ex) {
    log.warn(Constants.NOT_VALIDATED_ELEMENT, ex);
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      error.addValidationError(violation.getPropertyPath().toString(), violation.getMessage());
    }
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.warn(Constants.NOT_VALIDATED_ELEMENT, ex);
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return error;
  }
}
