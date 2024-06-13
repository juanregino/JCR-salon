package com.jcr.salon.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Juan Pablo Regino", url = "https://github.com/juanregino", email = "juanreginopenagos@gmail.com"), title = "Documentation: JCR Salon", version = "1.0.0"),

 security = {
  @SecurityRequirement(name = "bearerAuth")}


)
    @Server(description = "Local ENV", url = "http://localhost:8080/api/v1")
@SecurityScheme(name = "bearerAuth", description = "JWT token from nest authentication service", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {
  
}
