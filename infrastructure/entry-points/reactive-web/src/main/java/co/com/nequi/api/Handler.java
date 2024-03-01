package co.com.nequi.api;

import co.com.nequi.api.dto.ResponseDto;
import co.com.nequi.model.config.OnboardingException;
import co.com.nequi.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final UserUseCase userUseCase;

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest) {
        Integer id = Integer.valueOf(serverRequest.pathVariable("id"));

        return userUseCase.saveUser(id)
                .flatMap(user ->
                        ServerResponse.ok()
                                .bodyValue(user))
                .onErrorResume(OnboardingException.class, e ->
                        ServerResponse.status(
                                e.getError().getStatus())
                                .bodyValue(ResponseDto.builder()
                                        .message(e.getMessage())
                                        .data(e.getObject())
                                        .build()));
    }

}
