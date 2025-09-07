package co.com.bancolombia.api.dto;

import java.time.Instant;

public class LoginDTO {
    public record LoginRequest(
            String email,
            String password
    ) {
    }

    public record LoginResponse(
            String token,
            String type,
            Instant expiresAt
    ) {
    }
}
