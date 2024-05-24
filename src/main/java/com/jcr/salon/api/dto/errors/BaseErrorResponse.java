package com.jcr.salon.api.dto.errors;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseErrorResponse implements Serializable{
  private String status;
  private Integer code;
  
}
