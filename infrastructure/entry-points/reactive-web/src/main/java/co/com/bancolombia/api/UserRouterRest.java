package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import co.com.bancolombia.api.dto.UserRequestDTO;
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
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserRouterRest {

    private final UserPath userPath;
    private final UserHandler userHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/api/v1/usuarios",
                    beanClass = UserHandler.class,
                    beanMethod = "listenSaveUser",
                    operation = @Operation(
                            operationId = "createUser",
                            summary = "Crear un nuevo usuario",
                            description = "Crea un usuario con la información proporcionada",
                            requestBody = @RequestBody(
                                    required = true,
                                    description = "Datos del usuario a registrar",
                                    content = @Content(
                                            schema = @Schema(implementation = UserRequestDTO.class)
                                    )
                            ),
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente",
                                            content = @Content(mediaType = "application/json",
                                                    schema = @Schema(implementation = String.class)))
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routerFunction(UserHandler userHandler) {
        return route(POST(userPath.getUser()), userHandler::listenSaveUser)
                .and(route(GET(userPath.getUserById()), userHandler::listenGetUserById));
    }
}
