package co.com.bancolombia.usecase.rol;

import co.com.bancolombia.model.rol.Rol;
import co.com.bancolombia.model.rol.gateways.RolRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RolUseCase {

    private final RolRepository rolRepository;

    public Mono<Rol> createRol(Rol rol){
        return rolRepository.save(rol);
    }

    public Mono<Rol> getRolById(Long id){
        return rolRepository.findById(id);
    }

    public Flux<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    public Mono<Void> deleteRol(Long id){
        return rolRepository.deleteById(id);
    }
}
