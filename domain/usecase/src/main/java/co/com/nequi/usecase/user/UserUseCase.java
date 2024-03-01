package co.com.nequi.usecase.user;

import co.com.nequi.model.config.ErrorCode;
import co.com.nequi.model.config.OnboardingException;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.SingleUserRepository;
import co.com.nequi.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final SingleUserRepository singleUserRepository;

    public Mono<User> saveUser(Integer id) {
        return getSingleUser(id)
                .flatMap(user -> userRepository.findByEmail(user.getEmail())
                        .hasElement()
                        .flatMap(existingUser ->
                                Boolean.TRUE.equals(existingUser) ?
                                        userRepository.findByEmail(user.getEmail())
                                                .flatMap(existing -> Mono.error(new OnboardingException(ErrorCode.B409001, existing)))
                                        : Mono.just(user)
                        )
                        .flatMap(userRepository::save));
    }

    private Mono<User> getSingleUser(Integer id) {
        return singleUserRepository.getSingleUser(id)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000, "There is not a single user")));
    }

    public Mono<User> getUserById(Integer id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.B404000, "The user does not exist")));
    }

    public Flux<User> getUsers() {
        return userRepository.findAll()
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000)));
    }

    public Flux<User> getUsersByFirstName(String value) {
        return userRepository.getUsersByFirstName(value)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000)));
    }

}
