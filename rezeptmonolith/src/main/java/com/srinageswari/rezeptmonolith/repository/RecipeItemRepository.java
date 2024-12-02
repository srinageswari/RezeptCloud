package com.srinageswari.rezeptmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.srinageswari.rezeptmonolith.model.RecipeItemEntity;

import java.util.Optional;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeItemRepository extends JpaRepository<RecipeItemEntity, Long> {

  Optional<RecipeItemEntity> findByRecipeIdAndItemId(Long recipeId, Long itemId);

}
