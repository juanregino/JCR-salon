package com.jcr.salon.api.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {
  private LocalDateTime date;
  private Integer duration;
  private String comment;

  private UUID clientId;
  private UUID serviceId; 
  private UUID employeeId;
   
}
