package com.srinageswari.rezeptmonolith.service.recipeitem;

import com.srinageswari.rezeptmonolith.dto.RecipeItemDTO;

/**
 * @author smanickavasagam
 */
public interface IRecipeItemService {
  public RecipeItemDTO addItemToRecipe(RecipeItemDTO recipeItemDTO);

  public void removeItemFromRecipe(Long recipeId, Long itemId);
}
