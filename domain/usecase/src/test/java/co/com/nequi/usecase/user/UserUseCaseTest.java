package co.com.nequi.usecase.user;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;


@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private SingleUserRepository singleUserRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDynamoRepository userDynamoRepository;

    @Mock
    private RedisRepository redisRepository;

    @Mock
    private SQSSenderRepository sqsSenderRepository;

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1)
                .email("camilo@correo.com")
                .firstName("Camilo")
                .lastName("Monje")
                .avatar("http://123.com")
                .build();
    }

    @Test
    void saveUser() {

        when(singleUserRepository.getSingleUser(anyInt())).thenReturn(Mono.just(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());
        when(userRepository.save(any())).thenReturn(Mono.just(user));
        when(sqsSenderRepository.sendEvent(any())).thenReturn(Mono.just("user sent"));

        create(userUseCase.saveUser(1))
                .expectSubscription()
                .expectNextMatches(user1 -> Objects.equals(user1.getEmail(), user.getEmail()))
                .verifyComplete();
    }

    @Test
    void saveUserExist() {

        when(singleUserRepository.getSingleUser(anyInt())).thenReturn(Mono.just(user));
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));

        create(userUseCase.saveUser(1))
                .expectSubscription()
                .expectError()
                .verify();

    }

    @Test
    void getUserById() {

        when(redisRepository.getById(anyInt())).thenReturn(Mono.empty());
        when(userRepository.findById(anyInt())).thenReturn(Mono.just(user));
        when(redisRepository.save(any())).thenReturn(Mono.just(user));

        create(userUseCase.getUserById(1))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void getUsers() {

        when(userRepository.findAll()).thenReturn(Flux.just(user));
        when(redisRepository.save(any())).thenReturn(Mono.just(user));

        create(userUseCase.getUsers())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getUsersByFirstName() {

        when(userRepository.getUsersByFirstName(anyString())).thenReturn(Flux.just(user));

        create(userUseCase.getUsersByFirstName("a"))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void saveUserUpperCase() {

        when(userDynamoRepository.save(any())).thenReturn(Mono.just(user));

        create(userUseCase.saveUserUpperCase(user))
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }
}