package com.srinageswari.rezeptmonolith.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @param <T>
 * @author smanickavasagam
 *     <p>Builds API response in a proper format with timestamp, message and data
 */
@Data
public class APIResponseDTO<T> {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;

  long size;

  private final String message;
  private final T data;

  public APIResponseDTO(LocalDateTime timestamp, String message, T data, long size) {
    this.timestamp = timestamp;
    this.message = message;
    this.data = data;
    this.size = size;
  }
}
