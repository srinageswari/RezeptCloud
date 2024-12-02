package com.srinageswari.rezeptmonolith.service.itemtype;

import com.srinageswari.rezeptmonolith.common.exception.helper.ElementAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.srinageswari.rezeptmonolith.common.Constants;
import com.srinageswari.rezeptmonolith.common.exception.helper.NoSuchElementFoundException;
import com.srinageswari.rezeptmonolith.common.search.SearchSpecification;
import com.srinageswari.rezeptmonolith.dto.ItemtypeDTO;
import com.srinageswari.rezeptmonolith.dto.common.SearchRequestDTO;
import com.srinageswari.rezeptmonolith.mapper.ItemtypeMapper;
import com.srinageswari.rezeptmonolith.model.ItemtypeEntity;
import com.srinageswari.rezeptmonolith.repository.ItemtypeRepository;

/**
 * @author smanickavasagam
 *     <p>Service used for adding, removing, updating itemtype
 */
@Slf4j(topic = "CategoryService")
@Service
@RequiredArgsConstructor
public class ItemtypeServiceImpl implements IItemtypeService {

  private final ItemtypeRepository itemtypeRepository;
  private final ItemtypeMapper itemtypeMapper;

  /**
   * Fetches a itemtype by the given id
   *
   * @param id
   * @return
   */
  @Transactional(readOnly = true)
  public ItemtypeDTO findById(Long id) {
    return itemtypeRepository
        .findById(id)
        .map(itemtypeMapper::toDto)
        .orElseThrow(
            () -> {
              log.error(Constants.NOT_FOUND_RECORD);
              return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
            });
  }

  /**
   * Fetches all itemtypes based on the given itemtype filter parameters
   *
   * @param request
   * @return Paginated itemtype data
   */
  @Transactional(readOnly = true)
  public Page<ItemtypeDTO> findAll(SearchRequestDTO request) {
    final SearchSpecification<ItemtypeEntity> specification = new SearchSpecification<>(request);
    final Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());
    final Page<ItemtypeDTO> itemtypes =
        itemtypeRepository.findAll(specification, pageable).map(itemtypeMapper::toDto);
    if (itemtypes.isEmpty()) {
      log.error(Constants.NOT_FOUND_RECORD);
      throw new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
    }
    return itemtypes;
  }

  /**
   * Creates a new itemtype using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemtypeDTO create(ItemtypeDTO request) {
    if (itemtypeRepository.findByType(request.getType())!=null) {
      log.error(Constants.ALREADY_EXISTS);
      throw new ElementAlreadyExistsException(Constants.ALREADY_EXISTS);
    }
    return itemtypeMapper.toDto(itemtypeRepository.save(itemtypeMapper.toEntity(request)));
  }

  /**
   * Updates itemtype using the given request parameters
   *
   * @param request
   * @return
   */
  @Transactional
  public ItemtypeDTO update(ItemtypeDTO request) {
    final ItemtypeEntity itemtypeEntity =
        itemtypeRepository
            .findById(request.getTypeId())
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECORD);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
                });
    request.setVersion(itemtypeEntity.getVersion());
    return itemtypeMapper.toDto(itemtypeRepository.save(itemtypeMapper.toEntity(request)));
  }

  /**
   * Deletes itemtype by the given id
   *
   * @param id
   * @return
   */
  @Transactional
  public void deleteById(Long id) {
    final ItemtypeEntity itemtypeEntity =
        itemtypeRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error(Constants.NOT_FOUND_RECORD);
                  return new NoSuchElementFoundException(Constants.NOT_FOUND_RECORD);
                });
    itemtypeRepository.delete(itemtypeEntity);
  }
}
