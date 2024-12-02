package com.srinageswari.rezeptmonolith.service.recipeitem;

import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.common.exception.helper.NoSuchElementFoundException;
import com.srinageswari.rezeptmonolith.dto.RecipeItemDTO;
import com.srinageswari.rezeptmonolith.mapper.RecipeItemMapper;
import com.srinageswari.rezeptmonolith.model.ItemEntity;
import com.srinageswari.rezeptmonolith.model.ItemtypeEntity;
import com.srinageswari.rezeptmonolith.model.RecipeEntity;
import com.srinageswari.rezeptmonolith.model.RecipeItemEntity;
import com.srinageswari.rezeptmonolith.repository.ItemRepository;
import com.srinageswari.rezeptmonolith.repository.ItemtypeRepository;
import com.srinageswari.rezeptmonolith.repository.RecipeItemRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding and removing recipeItem
 */
@Slf4j(topic = "RecipeItemService")
@Service
@RequiredArgsConstructor
public class RecipeItemServiceImpl implements IRecipeItemService {

  private final RecipeItemRepository recipeItemRepository;
  private final ItemRepository itemRepository;
  private final ItemtypeRepository itemtypeRepository;
  private final RecipeItemMapper recipeItemMapper;

  /**
   * Adds item to the given recipe
   *
   * @param recipeItemDTO
   * @return
   */
  @Transactional
  public RecipeItemDTO addItemToRecipe(RecipeItemDTO recipeItemDTO) {
    final ItemEntity itemEntity = processRecipeItem(recipeItemDTO);
    final RecipeEntity recipeEntity = new RecipeEntity(recipeItemDTO.getRecipe().getId());
    final RecipeItemEntity recipeItemEntity =
        new RecipeItemEntity(
            recipeEntity,
            itemEntity,
            recipeItemDTO.getRequiredQty(),
            recipeItemDTO.getCulinaryStep(),
            recipeItemDTO.getRecipeUnit()
        );
    return recipeItemMapper.toDto(recipeItemRepository.save(recipeItemEntity));
  }

  /**
   * Removes item from the given recipe
   *
   * @param recipeId
   * @param itemId
   * @return
   */
  @Transactional
  public void removeItemFromRecipe(Long recipeId, Long itemId) {
    final RecipeItemEntity recipeItemEntity =
        recipeItemRepository
            .findByRecipeIdAndItemId(recipeId, itemId)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });
    recipeItemRepository.delete(recipeItemEntity);
  }

  @Transactional
  public void handleRecipeItems(RecipeDTO request, RecipeEntity recipeEntity)
  {
        request
                .getRecipeItems()
                .forEach(
                        recipeItemDTO ->
                                recipeEntity.addRecipeItem(
                                        new RecipeItemEntity(
                                                recipeEntity,
                                                processRecipeItem(recipeItemDTO),
                                                recipeItemDTO.getRequiredQty(),
                                                recipeItemDTO.getCulinaryStep(),
                                                recipeItemDTO.getRecipeUnit()
                                        )));
  }

  @Transactional
  public ItemEntity processRecipeItem(RecipeItemDTO recipeItemDTO) {
    ItemEntity itemEntity=
        itemRepository
                .findItemByName(recipeItemDTO.getItem().getName());
        if(itemEntity == null)
        {
                  ItemtypeEntity itemtype =
                      itemtypeRepository.findByType(
                          recipeItemDTO.getItem().getItemtype().getType());
                  if (itemtype == null) {
                    itemtype =
                        itemtypeRepository.save(
                            new ItemtypeEntity(
                                null, recipeItemDTO.getItem().getItemtype().getType()));
                  }
                  return itemRepository.save(
                      new ItemEntity(null, recipeItemDTO.getItem().getName(), itemtype));
        };
    return itemEntity;
  }
}
