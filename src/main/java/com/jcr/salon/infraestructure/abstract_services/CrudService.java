package com.jcr.salon.infraestructure.abstract_services;
/*
 * RQ: Request
 * RS: Response
 * ID: Id
 */


import org.springframework.data.domain.Page;

import com.jcr.salon.utils.enums.SortType;

public interface CrudService <RQ,RS,ID> {
  public RS create(RQ request);
  public RS update(RQ request , ID id);
  public void delete(ID id);
  public RS findById(ID id);
  public Page<RS> getAll(int page, int size, SortType sort);
}
