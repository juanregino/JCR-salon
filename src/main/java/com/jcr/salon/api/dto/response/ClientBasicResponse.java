package com.jcr.salon.api.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientBasicResponse {
  private UUID id;
  private String name;
  private String lastName;
  private String phone;
  private String email;
}
