package co.com.bancolombia.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long idUser;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private String phone;
    private BigDecimal baseSalary;
    private Long idRol;
    private String password;
}
