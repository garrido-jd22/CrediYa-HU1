package co.com.bancolombia.api.dto;

import co.com.bancolombia.model.user.User;

import java.math.BigDecimal;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String lastName,
        String identityDocument,
        String phone,
        BigDecimal baseSalary,
        Long idRol
) {

    public static UserResponseDTO fromDomain(User user) {
        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                // Controlar que la cedula no se repita.
                user.getLastName(),
                user.getIdentityDocument(),
                user.getPhone(),
                user.getBaseSalary(),
                user.getIdRol()
        );
    }
}
