package co.com.nequi.r2dbc;

import co.com.nequi.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserDataRepository repository;

    @Mock
    ObjectMapper mapper;

    private User user;
    private UserData userData;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1)
                .email("camilo@correo.com")
                .firstName("Camilo")
                .lastName("Monje")
                .avatar("http://123.com")
                .build();

        userData = UserData.builder()
                .id(1)
                .email("camilo@correo.com")
                .firstName("Camilo")
                .lastName("Monje")
                .avatar("http://123.com")
                .build();
    }

    @Test
    void mustFindValueById() {

        when(repository.findById(1)).thenReturn(Mono.just(userData));
        when(mapper.map(userData, User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findById(1);

        StepVerifier.create(result)
                .expectNextMatches(value -> Objects.equals(value.getEmail(), user.getEmail()))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(userData));
        when(mapper.map(userData, User.class)).thenReturn(user);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> Objects.equals(value.getEmail(), user.getEmail()))
                .verifyComplete();
    }

}
