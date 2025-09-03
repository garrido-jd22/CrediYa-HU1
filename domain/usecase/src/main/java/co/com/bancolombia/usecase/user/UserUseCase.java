package co.com.bancolombia.usecase.user;

import co.com.bancolombia.model.exception.EmailAlreadyExistsException;
import co.com.bancolombia.model.exception.InvalidSalaryException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.PasswordEncoderGateway;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public Mono<User> createUser(User user) {
        if (user.getBaseSalary().compareTo(BigDecimal.valueOf(15000000)) > 0) {
            return Mono.error(new InvalidSalaryException(
                    "El salario base no puede superar los 15,000,000"
            ));
        }
        user.setPassword(passwordEncoderGateway.encode(user.getPassword()));
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existingUser -> {
                    if (existingUser != null) {
                        return Mono.error(new EmailAlreadyExistsException(
                                "El correo electrónico " + existingUser.getEmail() + " ya está registrado."
                        ));
                    }
                    return Mono.just(user);
                })
                .switchIfEmpty(Mono.just(user))
                .flatMap(userRepository::save);
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

}
