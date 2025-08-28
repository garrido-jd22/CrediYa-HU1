package co.com.bancolombia.model.rol.gateways;

import co.com.bancolombia.model.rol.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

 // Port Out
public interface RolRepository {
    Mono<Rol> save(Rol rol);
    Mono<Rol> findById(Long id);
    Flux<Rol> findAll();
    Mono<Void> deleteById(Long id);
}
