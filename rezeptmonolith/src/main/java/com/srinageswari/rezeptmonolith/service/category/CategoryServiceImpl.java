package com.srinageswari.rezeptmonolith.service.category;

import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.common.exception.helper.NoSuchElementFoundException;
import com.srinageswari.rezeptmonolith.common.search.SearchSpecification;
import com.srinageswari.rezeptmonolith.dto.CategoryDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.mapper.CategoryMapper;
import com.srinageswari.rezeptmonolith.model.CategoryEntity;
import com.srinageswari.rezeptmonolith.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating category
 */
@Slf4j(topic = "CategoryService")
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  /**
   * Fetches a category by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
    return categoryRepository
        .findById(id)
        .map(categoryMapper::toDto)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECORD);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
            });
  }

  /**
   * Fetches all categories based on the given category filter parameters
   *
   * @param request
   * @return Paginated category data
   */
  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<CategoryEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<CategoryDTO> categories =
        categoryRepository.findAll(specification, pageable).map(categoryMapper::toDto);
    if (categories.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return categories;
  }

  /**
   * Creates a new category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CategoryDTO create(CategoryDTO request) {
    final CategoryEntity categoryEntity = categoryMapper.toEntity(request);
    return categoryMapper.toDto(categoryRepository.save(categoryEntity));
  }

  /**
   * Updates category using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public CategoryDTO update(CategoryDTO request) {
    final CategoryEntity categoryEntity =
        categoryRepository
            .findById(request.getCategoryId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECORD);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
                });
    categoryEntity.setName(request.getName());
    categoryEntity.setMeal(request.getMeal());
    categoryEntity.setDifficulty(request.getDifficulty());
    categoryEntity.setSidedish(request.isSidedish());
    return categoryMapper.toDto(categoryRepository.save(categoryEntity));
  }

  /**
   * Deletes category by the given id
   *
   * @param id
   * @return
   */
  @Transactional
  public void deleteById(Long id) {
    final CategoryEntity categoryEntity =
        categoryRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECORD);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
                });
    categoryRepository.delete(categoryEntity);
  }
}
