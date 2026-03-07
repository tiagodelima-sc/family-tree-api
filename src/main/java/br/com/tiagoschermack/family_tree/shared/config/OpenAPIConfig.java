package br.com.tiagoschermack.family_tree.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP;

@Configuration
@SuppressWarnings("unused")
public class OpenAPIConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title      ("Family Tree API")
                        .description("API REST para gerenciamento de árvores genealógicas")
                        .version    ("1.0.0")
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")
                )
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type        (HTTP)
                                .scheme      ("bearer")
                                .bearerFormat("JWT")
                                .description ("Informe o token JWT obtido no endpoint de login")
                        )
                );
    }
}
