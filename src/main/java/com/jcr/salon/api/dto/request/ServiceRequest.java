package com.jcr.salon.api.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest {
  private String name;
  private String description;
  private BigDecimal price;
}
