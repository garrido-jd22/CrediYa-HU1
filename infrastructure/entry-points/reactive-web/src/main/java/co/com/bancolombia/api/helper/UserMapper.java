package co.com.bancolombia.api.helper;

import co.com.bancolombia.api.dto.UserRequestDTO;
import co.com.bancolombia.api.dto.UserResponseDTO;
import co.com.bancolombia.model.user.User;

public class UserMapper {

    // De DTO de request a modelo de dominio
    public static User toDomain(UserRequestDTO dto) {
        return User.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .identityDocument(dto.identityDocument())
                .email(dto.email())
                .phone(dto.phone())
                .baseSalary(dto.baseSalary())
                .password(dto.password())
                .idRol(dto.idRol())
                .build();
    }

    // De modelo de dominio a DTO de response
    public static UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getLastName(),
                user.getIdentityDocument(),
                user.getEmail(),
                user.getPhone(),
                user.getBaseSalary(),
                user.getIdRol()
        );
    }
}
