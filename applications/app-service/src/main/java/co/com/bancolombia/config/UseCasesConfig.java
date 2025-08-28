package co.com.bancolombia.config;

import co.com.bancolombia.model.rol.gateways.RolRepository;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.usecase.rol.RolUseCase;
import co.com.bancolombia.usecase.transaction.TransactionExecutor;
import co.com.bancolombia.usecase.user.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository, TransactionExecutor transactionExecutor) {
        return new UserUseCase(userRepository, transactionExecutor);
    }

    @Bean
    public RolUseCase rolUseCase(RolRepository rolRepository) {
        return new RolUseCase(rolRepository);
    }
}
