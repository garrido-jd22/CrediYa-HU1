package co.com.bancolombia.api.helper;

import co.com.bancolombia.api.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponse")
public record LoginResponseWrapper(
        boolean success,
        String message,
        UserResponseDTO data
) {}

