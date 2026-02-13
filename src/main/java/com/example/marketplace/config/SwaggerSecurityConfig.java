package com.example.marketplace.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@SecurityScheme(
        name = "swagger_keycloak",
        scheme = "bearer",
        type = SecuritySchemeType.OPENIDCONNECT,
        in = SecuritySchemeIn.HEADER,
        openIdConnectUrl = "http://localhost:9180/auth/realms/kyano/.well-known/openid-configuration"
)
public class SwaggerSecurityConfig {

}