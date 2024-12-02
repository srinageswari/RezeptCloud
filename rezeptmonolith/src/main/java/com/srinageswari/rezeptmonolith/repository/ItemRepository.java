package com.srinageswari.rezeptmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.srinageswari.rezeptmonolith.model.ItemEntity;

import java.util.List;

/**
 * @author smanickavasagam
 */
@Repository
public interface ItemRepository
    extends JpaRepository<ItemEntity, Long>, JpaSpecificationExecutor<ItemEntity> {

  boolean existsByNameIgnoreCase(String name);
  @Query(
          value ="SELECT * FROM item i WHERE i.name = :name", nativeQuery = true)
  ItemEntity findItemByName(String name);

}
