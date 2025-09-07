package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operaciones de autenticación de usuarios.")
public class AuthRouterRest {

    private final UserPath userPath;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/login",
                    beanClass = AuthHandler.class,
                    beanMethod = "login",
                    operation = @Operation(
                            operationId = "loginUser",
                            summary = "Autenticación",
                            description = "Autenticación de usuario.",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del usuario para iniciar sesión",
                                    content = @Content(
                                            schema = @Schema(implementation = LoginDTO.LoginRequest.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = LoginDTO.LoginResponse.class))),
                                    @ApiResponse(responseCode = "401", description = "Error de autenticación",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = co.com.bancolombia.api.dto.ApiResponse.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> authRoutes(AuthHandler authHandler) {
        return RouterFunctions.route()
                .POST(userPath.getUserLogin(), authHandler::login)
                .build();
    }
}
