package co.com.bancolombia.model.user.gateways;

import co.com.bancolombia.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// Port Out
public interface UserRepository {
    Mono<User> save(User user);
    Mono<User> findById(Long id);
    Mono<User> findByEmail(String email);
    Mono<User> update(User user);
    Flux<User> findAll();
    Mono<Void> deleteById(Long id);
}
