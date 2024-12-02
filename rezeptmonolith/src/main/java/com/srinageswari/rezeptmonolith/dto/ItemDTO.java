package com.srinageswari.rezeptmonolith.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.srinageswari.rezeptmonolith.enums.Unit;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author smanickavasagam
 *     <p>Data Transfer Object for Item
 */
@Data
@NoArgsConstructor
public class ItemDTO {
  @JsonProperty("itemId")
  private Long id;

  @JsonProperty("name")
  @NotNull
  private String name;

  @JsonProperty("itemStockQty")
  private int itemStockQty;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("stockedDt")
  private Date stockedDt;

  @JsonProperty("itemtype")
  @NotNull
  private ItemtypeDTO itemtype;

  @JsonProperty("inStock")
  private boolean inStock;

  @JsonProperty("stockUnit")
  private Unit stockUnit;
}
