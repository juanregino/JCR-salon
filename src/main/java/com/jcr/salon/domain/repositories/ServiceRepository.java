package com.jcr.salon.domain.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcr.salon.domain.entities.ServiceEntity;

@Repository
public interface ServiceRepository  extends  JpaRepository<ServiceEntity, UUID> {
  @Query("SELECT s FROM service s WHERE s.price BETWEEN :min AND :max") 
  public List<ServiceEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);
  // @Query("SELECT s FROM service s WHERE s.name LIKE :name")  
  public List<ServiceEntity> findByNameContaining (String name); 

}
