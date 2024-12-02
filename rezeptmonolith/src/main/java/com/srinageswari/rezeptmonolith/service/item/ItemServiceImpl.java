package com.srinageswari.rezeptmonolith.service.item;

import com.srinageswari.rezeptmonolith.model.CategoryEntity;
import com.srinageswari.rezeptmonolith.model.ItemtypeEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.common.exception.helper.ElementAlreadyExistsException;
import com.srinageswari.rezeptmonolith.common.exception.helper.NoSuchElementFoundException;
import com.srinageswari.rezeptmonolith.common.search.SearchSpecification;
import com.srinageswari.rezeptmonolith.dto.ItemDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.mapper.ItemMapper;
import com.srinageswari.rezeptmonolith.model.ItemEntity;
import com.srinageswari.rezeptmonolith.repository.ItemRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating item
 */
@Slf4j(topic = "ItemService")
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements IItemService {

  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;
  private final EntityManager entityManager;

  /**
   * Fetches an item by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public ItemDTO findById(Long id) {
    return itemRepository
        .findById(id)
        .map(itemMapper::toDto)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_ITEM);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
            });
  }

  /**
   * Fetches all items based on the given filter parameters
   *
   * @param request
   * @return Paginated item data
   */
  @Transactional(readOnly = true)
  public Page<ItemDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<ItemEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemDTO> items =
        itemRepository.findAll(specification, pageable).map(itemMapper::toDto);
    if (items.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return items;
  }

  /**
   * Creates a new item using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemDTO create(ItemDTO request) {
    if (itemRepository.existsByNameIgnoreCase(request.getName())) {
      log.error(Constants.ALREADY_EXISTS_ITEM);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS_ITEM);
    }
    return itemMapper.toDto(itemRepository.save(constructItemEntity(request)));
  }

  /**
   * Updates item using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemDTO update(ItemDTO request) {
    final ItemEntity itemEntity =
        itemRepository
            .findById(request.getId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });
    return itemMapper.toDto(itemRepository.save(constructItemEntity(request)));
  }

  /**
   * Deletes item by the given id
   *
   * @param id
   * @return
   */
  @Transactional
  public void deleteById(Long id) {
    final ItemEntity itemEntity =
        itemRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_ITEM);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_ITEM);
                });
    itemRepository.delete(itemEntity);
  }


  @Transactional
  public ItemEntity constructItemEntity(ItemDTO request) {
    ItemEntity itemEntity = itemMapper.toEntity(request);
    // Fetch ItemTypeEntity based on typeId
    ItemtypeEntity itemtypeEntity = entityManager.find(ItemtypeEntity.class, request.getItemtype().getTypeId());
    // Set the itemtypeentity in itementity
    itemEntity.setItemtype(itemtypeEntity);
    return itemEntity;
  }

}
