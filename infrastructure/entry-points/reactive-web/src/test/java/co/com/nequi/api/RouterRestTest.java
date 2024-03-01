package co.com.nequi.api;

import co.com.nequi.api.dto.ResponseDto;
import co.com.nequi.model.config.ErrorCode;
import co.com.nequi.model.config.OnboardingException;
import co.com.nequi.model.user.User;
import co.com.nequi.usecase.user.UserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest({Handler.class})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
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

        when(userUseCase.saveUser(anyInt())).thenReturn(Mono.just(user));

        webTestClient.post()
                .uri("/api/users/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }

    @Test
    void saveUserError() {

        when(userUseCase.saveUser(anyInt())).thenReturn(Mono.error(new OnboardingException(ErrorCode.B409001)));

        webTestClient.post()
                .uri("/api/users/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ResponseDto.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }

    @Test
    void getUsers() {

        when(userUseCase.getUsers()).thenReturn(Flux.just(user));

        webTestClient.get()
                .uri("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }

    @Test
    void getUserById() {

        when(userUseCase.getUserById(anyInt())).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/api/users?id=1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }

    @Test
    void getUsersByFirstName() {

        when(userUseCase.getUsersByFirstName(anyString())).thenReturn(Flux.just(user));

        webTestClient.get()
                .uri("/api/users?value=a")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(List.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }

    @Test
    void getUsersError() {

        webTestClient.get()
                .uri("/api/users?id=2&value=a")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(String.class)
                .value(response -> Assertions.assertThat(response).isNotNull());
    }


}
