package co.com.nequi.consumer;

import co.com.nequi.consumer.mapper.UserMapper;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.SingleUserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestConsumer implements SingleUserRepository {
    private final WebClient client;

    @Override
    @CircuitBreaker(name = "getSingleUser")
    public Mono<User> getSingleUser(Integer id) {
        return client
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/" + id)
                        .build())
                .retrieve()
                .bodyToMono(ObjectResponse.class)
                .map(UserMapper::toDomain);
    }

}
