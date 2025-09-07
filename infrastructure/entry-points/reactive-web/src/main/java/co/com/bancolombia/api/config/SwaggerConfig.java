package co.com.bancolombia.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final UserPath userPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de CrediYa")
                        .version("1.0")
                        .description("API REST Documentación de la API para autenticación, creación de usuarios y gestion de solicitudes.")
                        .contact(
                                new Contact().name("Soporte CrediYa")
                                        .email("soporte@crediya.com.co")
                                        .url("https://www.crediya.com")
                        ));
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .displayName("🌎 APIs Auth")
                .pathsToMatch(userPath.getUserLogin())
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("Usuarios")
                .displayName("👤 APIs para la creación de Usuarios")
                .pathsToMatch(userPath.getUser(), userPath.getUserById())
                .build();
    }
}

