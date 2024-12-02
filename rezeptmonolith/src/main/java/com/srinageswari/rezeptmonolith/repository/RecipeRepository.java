package com.srinageswari.rezeptmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import com.srinageswari.rezeptmonolith.model.RecipeEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * @author smanickavasagam
 */
@Repository
public interface RecipeRepository
    extends JpaRepository<RecipeEntity, Long>, JpaSpecificationExecutor<RecipeEntity> {

  @Query(
      value = "SELECT * FROM recipe r where r.category_id = :categoryId",
      nativeQuery = true)
  List<RecipeEntity> findRecipesByCategoryId(Long categoryId);


  @Query(
          value = "SELECT * FROM recipe r where r.title = :title",
          nativeQuery = true)
  RecipeEntity findByTitle(String title);
}
