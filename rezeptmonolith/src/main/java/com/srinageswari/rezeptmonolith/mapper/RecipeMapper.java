package com.srinageswari.rezeptmonolith.mapper;

import org.apache.commons.text.WordUtils;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import com.srinageswari.rezeptmonolith.model.RecipeEntity;

/**
 * @author smanickavasagam
 *     <p>Mapper for RecipeRequest
 */
@Mapper(
    componentModel = "spring",
    uses = {CategoryMapper.class, RecipeItemMapper.class})
@Component
public interface RecipeMapper {

  @Mapping(target = "recipeItems", source = "dto.recipeItems")
  // Map the recipeItems property
  RecipeEntity toEntity(RecipeDTO dto);

  RecipeDTO toDto(RecipeEntity entity);

  @AfterMapping
  default void getCapitalizedTitle(@MappingTarget RecipeEntity entity, RecipeDTO dto) {
    entity.setTitle(WordUtils.capitalizeFully(dto.getTitle()));
  }
}
