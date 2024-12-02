package com.srinageswari.rezeptmonolith.service.recipe;

import com.srinageswari.rezeptmonolith.mapper.CategoryMapper;
import com.srinageswari.rezeptmonolith.mapper.RecipeItemMapper;
import com.srinageswari.rezeptmonolith.service.category.CategoryServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.common.RecipeUtil;
import com.srinageswari.rezeptmonolith.common.exception.helper.NoSuchElementFoundException;
import com.srinageswari.rezeptmonolith.common.search.SearchSpecification;
import com.srinageswari.rezeptmonolith.dto.RecipeDTO;
import com.srinageswari.rezeptmonolith.dto.RecipeResponseDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.mapper.RecipeMapper;
import com.srinageswari.rezeptmonolith.model.*;
import com.srinageswari.rezeptmonolith.repository.RecipeRepository;
import com.srinageswari.rezeptmonolith.service.recipeitem.RecipeItemServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.text.WordUtils.capitalizeFully;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, updating, removing and fetching recipes
 */
@Slf4j(topic = "RecipeServiceImpl")
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {
  private final RecipeRepository recipeRepository;
  private final RecipeMapper recipeMapper;
  private final EntityManager entityManager;
  private final RecipeItemServiceImpl recipeItemServiceImpl;


  /**
   * Fetches a recipe by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public RecipeResponseDTO findById(Long id) {
    return recipeRepository
        .findById(id)
        .map(RecipeUtil::getRecipeResponseDTO)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECIPE);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
            });
  }

  /**
   * Fetches all recipes based on the given recipe filter parameters
   *
   * @param request
   * @return Paginated recipe data
   */
  @Transactional(readOnly = true)
  public Page<RecipeResponseDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<RecipeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<RecipeResponseDTO> recipes =
        recipeRepository.findAll(specification, pageable).map(RecipeUtil::getRecipeResponseDTO);
    if (recipes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return recipes;
  }

  /**
   * Creates a new recipe and items belonging to the recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public RecipeResponseDTO createOrUpdate(RecipeDTO request) {
    return RecipeUtil.getRecipeResponseDTO(recipeRepository.save(constructRecipeEntity(request)));
  }

  @Transactional
  public List<RecipeResponseDTO> bulkInsert(List<RecipeDTO> recipeDTOList) {
    List<RecipeEntity> entities = recipeDTOList.stream().map(this::constructRecipeEntity).toList();
    List<RecipeEntity> response = recipeRepository.saveAll(entities);
    return response.stream().map(RecipeUtil::getRecipeResponseDTO).toList();
  }

  /**
   * Updates recipe using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  // for adding/removing items for a current recipe, use RecipeItemService methods
  public RecipeResponseDTO update(RecipeDTO request) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeEntity.setTitle(capitalizeFully(request.getTitle()));
    recipeEntity.setReference(request.getReference());
    recipeEntity.setPrepTime(request.getPrepTime());
    recipeEntity.setCookTime(request.getCookTime());
    recipeEntity.setServings(request.getServings());
    recipeEntity.setInstructions(request.getInstructions());
    recipeEntity.setHealthLabel(request.getHealthLabel());
    return RecipeUtil.getRecipeResponseDTO(recipeRepository.save(recipeEntity));
  }

  /**
   * Deletes recipe by the given id
   *
   * @param id
   * @return
   */
  @Transactional
  public void deleteById(Long id) {
    final RecipeEntity recipeEntity =
        recipeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECIPE);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECIPE);
                });
    recipeRepository.delete(recipeEntity);
  }

  @Transactional(readOnly = true)
  public List<RecipeResponseDTO> getRecipeByCategoryId(Long id) {
    return recipeRepository.findRecipesByCategoryId(id).stream()
        .map(RecipeUtil::getRecipeResponseDTO)
        .toList();
  }

  @Transactional
  public RecipeEntity constructRecipeEntity(RecipeDTO request) {
    RecipeEntity recipeEntity = null;
      recipeEntity =  recipeRepository.findByTitle(request.getTitle());
    if (recipeEntity == null) {
      request.setId(RecipeUtil.uuidToLong(UUID.randomUUID()));
    } else  {
      request.setId(recipeEntity.getId());
    }
    recipeEntity = recipeMapper.toEntity(request);
    // Fetch CategoryEntity based on categoryId
    CategoryEntity categoryEntity = entityManager.find(CategoryEntity.class, request.getCategory().getCategoryId());

    // Set the CategoryEntity in RecipeEntity
    recipeEntity.setCategory(categoryEntity);

    recipeEntity.getRecipeItems().clear();
    recipeItemServiceImpl.handleRecipeItems(request,recipeEntity);
    return recipeEntity;
  }
}
