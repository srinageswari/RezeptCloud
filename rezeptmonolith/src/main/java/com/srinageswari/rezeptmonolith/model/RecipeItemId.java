package com.srinageswari.rezeptmonolith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RecipeItemId implements Serializable {

  @Column(name = "recipe_id", nullable = false, columnDefinition = "bigint UNSIGNED")
  private Long recipeId;

  @Column(name = "item_id", nullable = false)
  private Long itemId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecipeItemId that = (RecipeItemId) o;
    return recipeId.equals(that.recipeId) && itemId.equals(that.itemId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(recipeId, itemId);
  }
}
