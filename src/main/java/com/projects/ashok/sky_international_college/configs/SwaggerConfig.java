package com.projects.ashok.sky_international_college.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerCustomConfiguration(){
        return new OpenAPI().info(
                        new Info().title("Sky International College Application")
                                .description("This application is used to manage CMS for Sky International College")
                ).servers(
                        List.of(
                                new Server().url("http://localhost:8080").description("development")
                        )
                ).tags(
                        List.of(
                                new Tag().name("Auth Controller"),
                                new Tag().name("User Controller")

                        )
                ).addSecurityItem(
                        new SecurityRequirement().addList("bearerAuth"))
                .components(
                        new Components().addSecuritySchemes(
                                "bearerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("Bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name("Authorization")
                        )

                );
    }
}
