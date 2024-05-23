package com.jcr.salon.domain.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcr.salon.domain.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  
}
