package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.rol.Rol;
import co.com.bancolombia.model.rol.gateways.RolRepository;
import co.com.bancolombia.r2dbc.entity.RolEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RolRepositoryAdapter extends ReactiveAdapterOperations<
        Rol,
        RolEntity,
        Long,
        RolReactiveRepository
        > implements RolRepository {

    public RolRepositoryAdapter(RolReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, Rol.class));
    }

    @Override
    public Mono<Rol> save(Rol rol) {
        return super.save(rol);
    }

    @Override
    public Mono<Rol> findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Flux<Rol> findAll() {
        return super.findAll();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id);
    }
}
