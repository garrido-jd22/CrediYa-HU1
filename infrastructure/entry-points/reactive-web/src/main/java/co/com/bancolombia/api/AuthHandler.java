package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.LoginDTO;
import co.com.bancolombia.model.user.gateways.PasswordEncoderGateway;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginDTO.LoginRequest.class)
                .flatMap(loginRequest ->

                        userRepository.findByEmail(loginRequest.email())
                                .flatMap(user -> {
                                    if (passwordEncoderGateway.matches(loginRequest.password(), user.getPassword())) {
                                        String token = jwtProvider.generateToken(user.getEmail(), user.getIdRol());
                                        Instant expiresAt = jwtProvider.getExpirationFromToken(token);
                                        return ServerResponse.ok()
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .bodyValue(new LoginDTO.LoginResponse(token, "Bearer", expiresAt));
                                    } else {
                                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                                    }
                                })
                                .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build())
                );
    }

}
