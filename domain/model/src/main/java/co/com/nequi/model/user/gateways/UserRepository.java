package co.com.nequi.model.user.gateways;

import co.com.nequi.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);
    Mono<User> findByEmail(String email);
    Mono<User> findById(Integer id);
    Flux<User> findAll();
    Flux<User> getUsersByFirstName(String value);
}
