package co.com.nequi.api;

import co.com.nequi.api.dto.ResponseDto;
import co.com.nequi.model.config.ErrorCode;
import co.com.nequi.model.config.OnboardingException;
import co.com.nequi.model.user.User;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;

    private static final String ID = "id";
    private static final String VALUE = "value";

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable(ID));

        return userUseCase.saveUser(id)
                .flatMap(user ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .bodyValue(user))
                .onErrorResume(OnboardingException.class, e ->
                        ServerResponse.status(e.getError().getStatus())
                                .bodyValue(ResponseDto.builder()
                                        .message(e.getMessage())
                                        .data(e.getObject())
                                        .build()));
    }

    public Mono<ServerResponse> getUsers(ServerRequest serverRequest) {
        Optional<String> id = serverRequest.queryParam(ID);
        Optional<String> value = serverRequest.queryParam(VALUE);


        if (id.isPresent() && value.isPresent()) {
            return ServerResponse.status(HttpStatus.BAD_REQUEST)
                    .bodyValue(ResponseDto.builder()
                            .message(ErrorCode.B409002.getLog()).build());
        }

        Mono<List<User>> responseMono = id.map(s ->
                userUseCase.getUserById(Integer.valueOf(s))
                        .map(Collections::singletonList)
        ).orElseGet(() -> value.map(s ->
                userUseCase.getUsersByFirstName(s)
                        .collectList()
        ).orElseGet(() ->
                userUseCase.getUsers()
                        .collectList()
        ));

        return responseMono.flatMap(users -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(users))
                .onErrorResume(OnboardingException.class, e ->
                        ServerResponse.status(e.getError().getStatus())
                                .bodyValue(e.getMessage()));
    }

}
