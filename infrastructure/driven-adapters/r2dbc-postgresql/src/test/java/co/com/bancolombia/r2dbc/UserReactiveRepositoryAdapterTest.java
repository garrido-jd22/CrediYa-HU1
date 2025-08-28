package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class UserReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    private final User user = User.builder()
            .idUser(1L)
            .build();

    @Test
    void mustFindValueById() {

        when(repository.findById(1L).thenReturn(Mono.just(user)));

        when(mapper.map("test", User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(u -> u.getIdUser().equals(1L) && u.getPhone().equals("3172972651"))
                .verifyComplete();
    }
}
