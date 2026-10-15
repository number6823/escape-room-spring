package com.escape.room.escaperoombackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Escape Room Game API")
                        .description("방탈출 게임 프로젝트 백엔드 REST API 명세서")
                        .version("v1.0.0"))
                // 모든 API 호출에 JWT 인증 옵션 연결
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                // Swagger UI 상단에 Authorize(자물쇠) 버튼 생성
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}