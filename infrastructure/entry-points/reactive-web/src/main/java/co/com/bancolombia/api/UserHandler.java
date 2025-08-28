package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.ApiResponse;
import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.helper.UserMapper;
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
import co.com.bancolombia.model.exception.EmailAlreadyExistsException;
import co.com.bancolombia.model.exception.InvalidSalaryException;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserUseCase userUseCase;
    private final Validator validator;

    public Mono<ServerResponse> listenSaveUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequestDTO.class)
                .flatMap(dto -> {

                    Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
                    if (!violations.isEmpty()) {
                        String errorMessage = violations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(", "));
                        return Mono.error(new IllegalArgumentException(errorMessage));
                    }

                    return userUseCase.createUser(UserMapper.toDomain(dto));
                })
                .flatMap(savedUser -> {
                    String message = "Usuario " + savedUser.getName() + " " + savedUser.getLastName()
                            + " con el ID " + savedUser.getIdUser() + " creado con éxito";

                    return ServerResponse
                            .status(HttpStatus.CREATED)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(ApiResponse.success(message, savedUser));
                })
                .onErrorResume(EmailAlreadyExistsException.class, e ->
                        ServerResponse.status(HttpStatus.CONFLICT)
                                .bodyValue(Map.of("error", e.getMessage()))
                )
                .onErrorResume(InvalidSalaryException.class, e ->
                        ServerResponse.status(HttpStatus.BAD_REQUEST)
                                .bodyValue(Map.of("error", e.getMessage()))
                )
                .onErrorResume(IllegalArgumentException.class, e ->
                        ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(Map.of("error", e.getMessage()))
                );
    }

    public Mono<ServerResponse> listenGetUserById(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));

        return userUseCase.getUserById(id)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
