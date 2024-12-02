package com.srinageswari.rezeptmonolith.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.srinageswari.rezeptmonolith.model.ItemtypeEntity;

/**
 * @author smanickavasagam
 */
@Repository
public interface ItemtypeRepository
    extends JpaRepository<ItemtypeEntity, Long>, JpaSpecificationExecutor<ItemtypeEntity> {
  @Query(value = "SELECT * FROM itemtype i where i.type = :type", nativeQuery = true)
  ItemtypeEntity findByType(String type);
}
