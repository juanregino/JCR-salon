package com.jcr.salon.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@EqualsAndHashCode(callSuper = true)  
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterClientRequest extends RegisterRequest {
  private String name;
  private String lastName;
  private String phone;
  private String email; 
}
