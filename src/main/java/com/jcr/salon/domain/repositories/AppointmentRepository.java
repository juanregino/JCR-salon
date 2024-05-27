package com.jcr.salon.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jcr.salon.domain.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {  
  
// @Query(value = "SELECT a FROM appointment a JOIN FETCH a.client c WHERE c.id = :idClient")
   public Optional<Appointment> findByClientId(UUID idClient);
   
}
