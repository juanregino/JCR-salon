package com.jcr.salon.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcr.salon.domain.entities.ServiceEntity;

@Repository
public interface ServiceRepository  extends  JpaRepository<ServiceEntity, UUID> {
  
}
