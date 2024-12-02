package com.srinageswari.rezeptmonolith.enums;

import com.srinageswari.rezeptmonolith.common.Constants;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author smanickavasagam
 *     <p>Field types used for filtering
 */
@Slf4j
public enum FilterType {
  BOOLEAN {
    public Object parse(String value) {
      return Boolean.valueOf(value);
    }
  },
  CHAR {
    public Object parse(String value) {
      return value.charAt(0);
    }
  },
  DATE {
    public Object parse(String value) {
      Object date = null;
      try {
        date = LocalDateTime.parse(value, Constants.FORMATTER);
      } catch (Exception e) {
        log.warn(Constants.FIELD_PARSE_ERROR, e.getMessage());
      }
      return date;
    }
  },
  DOUBLE {
    public Object parse(String value) {
      return Double.valueOf(value);
    }
  },
  INTEGER {
    public Object parse(String value) {
      return Integer.valueOf(value);
    }
  },
  LONG {
    public Object parse(String value) {
      return Long.valueOf(value);
    }
  },
  STRING {
    public Object parse(String value) {
      return value;
    }
  };

  public abstract Object parse(String value);
}
