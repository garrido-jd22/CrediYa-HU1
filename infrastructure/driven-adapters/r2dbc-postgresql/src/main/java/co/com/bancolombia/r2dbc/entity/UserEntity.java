package co.com.bancolombia.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("usuario")
public class UserEntity {

    @Id
    @Column("id_usuario")
    private Long idUser;

    @Column("nombre")
    private String name;

    @Column("apellido")
    private String lastName;

    @Column("documento_identidad")
    private String identityDocument;

    @Column("telefono")
    private String phone;

    @Column("email")
    private String email;

    @Column("salario_base")
    private BigDecimal baseSalary;

    @Column("id_rol")
    private Long idRol;

    @Column("clave")
    private String password;
}
