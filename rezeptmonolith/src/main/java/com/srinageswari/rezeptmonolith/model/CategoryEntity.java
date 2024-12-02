package com.srinageswari.rezeptmonolith.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.srinageswari.rezeptmonolith.enums.Difficulty;
import com.srinageswari.rezeptmonolith.enums.Meal;

import java.util.Objects;
import java.util.Set;

/**
 * @author smanickavasagam
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class CategoryEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;

  @Version
  private Integer version;

  @Column(nullable = false, length = 50, unique = true)
  private String name;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Meal meal;

  // by default 0 - maindish, if 1 - sidedish
  private boolean sidedish;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Difficulty difficulty;

  @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
  private Set<RecipeEntity> recipes;

  public CategoryEntity(Long categoryId) {
    this.categoryId = categoryId;
  }

  public CategoryEntity(
      Long categoryId, String name, Meal meal, boolean sidedish, Difficulty difficulty) {
    this.categoryId = categoryId;
    this.name = name;
    this.meal = meal;
    this.sidedish = sidedish;
    this.difficulty = difficulty;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity)) return false;
    CategoryEntity categoryEntity = (CategoryEntity) o;
    return getCategoryId() != null
        && Objects.equals(getCategoryId(), categoryEntity.getCategoryId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
