package co.com.bancolombia.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("rol")
public class RolEntity {

    @Id
    @Column("id_rol")
    private Long idRol;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}
