package com.srinageswari.rezeptmonolith.model;

import jakarta.persistence.*;
import lombok.*;
import com.srinageswari.rezeptmonolith.enums.Cuisine;
import com.srinageswari.rezeptmonolith.enums.HealthLabel;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author smanickavasagam
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class RecipeEntity {

  @Id
  @Column(
      name = "id",
      columnDefinition = "bigint UNSIGNED DEFAULT (CONV(SUBSTR(UUID(), 1, 16), 16, 10))")
  private Long id;

  @Column(nullable = false, length = 50, unique = true)
  private String title;

  @Column(length = 100)
  private String reference;

  private Integer prepTime;

  private Integer cookTime;

  private Integer servings;

  @Lob
  @Column(nullable = false)
  private String instructions;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Cuisine cuisine;

  @Enumerated(value = EnumType.STRING)
  private HealthLabel healthLabel;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<RecipeItemEntity> recipeItems = new HashSet<>();

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "category_id")
  CategoryEntity category;

  private boolean isActive;

  private String notes;

  public void addRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.add(recipeItemEntity);
    recipeItemEntity.setRecipe(this);
  }

  public void removeRecipeItem(RecipeItemEntity recipeItemEntity) {
    recipeItems.remove(recipeItemEntity);
    recipeItemEntity.setRecipe(null);
  }

  public RecipeEntity(Long id) {
    this.id = id;
  }

  public RecipeEntity(
      Long id,
      String title,
      String reference,
      Integer prepTime,
      Integer cookTime,
      Integer servings,
      String instructions,
      HealthLabel healthLabel,
      Cuisine cuisine,
      boolean isActive) {
    this.id = id;
    this.title = title;
    this.reference = reference;
    this.prepTime = prepTime;
    this.cookTime = cookTime;
    this.servings = servings;
    this.instructions = instructions;
    this.healthLabel = healthLabel;
    this.cuisine = cuisine;
    this.isActive = isActive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RecipeEntity)) return false;
    RecipeEntity recipeEntity = (RecipeEntity) o;
    return getId() != null && Objects.equals(getId(), recipeEntity.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
