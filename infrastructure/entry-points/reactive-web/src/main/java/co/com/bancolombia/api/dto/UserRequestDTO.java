package co.com.bancolombia.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para registrar un usuario")
public record UserRequestDTO(

        Long idUser,

        @NotBlank(message = "El nombre es obligatorio")
        @Schema(description = "Nombre del usuario", example = "Josue")
        String name,

        @NotBlank(message = "El apellido es obligatorio")
        @Schema(description = "Apellido del usuario", example = "Garrido")
        String lastName,

        @NotBlank(message = "El documento de identidad es obligatorio")
        @Schema(description = "Documento de identidad", example = "1001918625")
        String identityDocument,

        @Email(message = "El correo electrónico no es válido")
        @NotBlank(message = "El email es obligatorio")
        @Schema(description = "Correo electrónico", example = "josuegarrido@gmail.com")
        String email,

        @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
        @Schema(description = "Teléfono", example = "3172972651")
        String phone,

        @DecimalMax(value = "15000000", message = "El salario base no debe ser mayor a $15000000")
        @NotNull(message = "El salario base es obligatorio")
        @Schema(description = "Salario base del usuario", example = "1200000")
        BigDecimal baseSalary,

        @NotNull(message = "El ID del rol es obligatorio")
        @Schema(description = "ID del rol", example = "2")
        Long idRol
) {
}
