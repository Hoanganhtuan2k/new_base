package com.example.ai_social_assistant.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI socialAssistantOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Social AI Assistant API")
                        .description("OpenAPI spec cho hệ thống trợ lý ảo quản lý kênh FB/IG")
                        .version("v1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project GitHub")
                        .url("https://github.com/example/social-ai-assistant"));
    }
}
