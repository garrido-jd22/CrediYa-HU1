package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.ApiResponse;
import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.dto.UserResponseDTO;
import co.com.bancolombia.api.helper.UserMapper;
import co.com.bancolombia.model.exception.UnauthorizedActionException;
import co.com.bancolombia.r2dbc.jwt.JwtProvider;
import co.com.bancolombia.api.transaction.TransactionExecutor;
import co.com.bancolombia.usecase.user.UserUseCase;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import jakarta.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final Validator validator;
    private final JwtProvider jwtProvider;
    private final TransactionExecutor transactionExecutor;

    // Agregar un Global Handler para manejar las excepciones.
    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        String authHeader = serverRequest.headers().firstHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED)
                    .bodyValue(Map.of("error", "Token requerido"));
        }
        String token = authHeader.substring(7);
        // Valido el token
        boolean isValid = jwtProvider.validateToken(token);
        if (!isValid) {
            return ServerResponse.status(HttpStatus.FORBIDDEN)
                    .bodyValue(Map.of("error", "Token inválido"));
        }

        return serverRequest.bodyToMono(UserRequestDTO.class)
                .flatMap(dto -> transactionExecutor.executeTransaction(() -> {

                    Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(", "));
                        return Mono.error(new IllegalArgumentException(errorMessage));
                    }
                    Long idRol = jwtProvider.getRoleIdFromToken(token);
                    if (!(idRol == 1 || idRol == 2)) { // Solo Admin o Asesor
                        return Mono.error(new UnauthorizedActionException("No tienes permisos para crear un usuario."));
                    }

                    return userUseCase.createUser(UserMapper.toDomain(dto));
                }))
                .flatMap(savedUser -> {
                    String message = "Usuario " + savedUser.getName() + " " + savedUser.getLastName()
                            + " con el ID " + savedUser.getIdUser() + " creado con éxito";

                    return ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(ApiResponse.success(message, UserMapper.toResponse(savedUser)));
                })
                .onErrorResume(Mono::error);
    }

    public Mono<ServerResponse> listenGetUserById(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));

        return transactionExecutor.executeTransaction(() ->
                userUseCase.getUserById(id)
                        .flatMap(user -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> listenGetUserByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");

        return transactionExecutor.executeTransaction(() ->
                userUseCase.getUserByEmail(email)
                        .flatMap(user -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(UserResponseDTO.fromDomain(user)))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }
}
