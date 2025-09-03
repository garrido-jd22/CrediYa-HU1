package co.com.bancolombia.api.dto;

public class LoginDTO {
    public record LoginRequest(String email, String password) {
    }

    public record LoginResponse(String token) {
    }
}
