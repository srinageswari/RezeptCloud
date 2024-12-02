package com.srinageswari.rezeptmonolith.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemtypeDTO {

  @JsonProperty("typeId")
  private Long typeId;

  @JsonProperty("type")
  @NotNull
  private String type;

  @JsonProperty("storageLife")
  private int storageLife;

  @JsonProperty("freshFridge")
  private boolean freshFridge;

  private int version;
}
