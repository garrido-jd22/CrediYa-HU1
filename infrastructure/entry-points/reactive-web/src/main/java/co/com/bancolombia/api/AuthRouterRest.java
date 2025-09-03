package co.com.bancolombia.api;

import co.com.bancolombia.api.config.UserPath;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class AuthRouterRest {

    private final UserPath userPath;

    @Bean
    public RouterFunction<ServerResponse> authRoutes(AuthHandler authHandler) {
        return RouterFunctions.route()
                .POST(userPath.getUserLogin(), authHandler::login)
                .build();
    }
}
