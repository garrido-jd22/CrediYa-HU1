package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.rol.Rol;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class RolReactiveRepositoryAdapterTest {

    @InjectMocks
    RolRepositoryAdapter repositoryAdapter;

    @Mock
    RolReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private final Rol rol = Rol.builder()
            .idRol(1L)
            .build();

    @Test
    void mustFindValueById() {

        when(repository.findById(1L).thenReturn(Mono.just(rol)));

        when(mapper.map("test", Rol.class)).thenReturn(rol);

        Mono<Rol> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(r -> r.getIdRol().equals(1L) && r.getName().equals("Administrador"))
                .verifyComplete();
    }
}
