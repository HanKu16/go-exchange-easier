package com.go_exchange_easier.backend.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String accessSchemeName = "cookieAccessAuth";
        String refreshSchemeName = "cookieRefreshAuth";
        String accessTokenCookieName = "accessToken";
        String refreshTokenCookieName = "refreshToken";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(accessSchemeName)
                        .addList(refreshSchemeName))
                .components(new Components()
                        .addSecuritySchemes(accessSchemeName, new SecurityScheme()
                                .name(accessTokenCookieName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .description("Access Token (HttpOnly Cookie)"))
                        .addSecuritySchemes(refreshSchemeName, new SecurityScheme()
                                .name(refreshTokenCookieName)
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .description("Refresh Token (HttpOnly Cookie)")));
    }

    @Bean
    public OperationCustomizer customizeGlobalHeaders() {
        return (operation, handlerMethod) -> {
            if (operation.getParameters() != null) {
                operation.getParameters().forEach(parameter -> {
                    if ("X-Device-Id".equalsIgnoreCase(parameter.getName())) {
                        parameter.setExample("550e8400-e29b-41d4-a716-446655440000");
                    }
                    if ("X-Device-Name".equalsIgnoreCase(parameter.getName())) {
                        parameter.setExample("Swagger-API-Browser");
                    }
                });
            }
            return operation;
        };
    }

}
