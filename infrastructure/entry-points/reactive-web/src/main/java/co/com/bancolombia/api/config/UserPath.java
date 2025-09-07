package co.com.bancolombia.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "routes.paths")
@Getter
@Setter
public class UserPath {
    private String userLogin;
    private String user;
    private String userById;
    private String userByEmail;
}
