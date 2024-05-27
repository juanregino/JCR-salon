package com.jcr.salon.api.dto.request;



import com.jcr.salon.utils.enums.RoleEmployee;

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
public class RegisterEmployeeRequest extends RegisterRequest {
   private String name;
   private String lastName;
   private String phone;
   private String email;
   private RoleEmployee role;
}
