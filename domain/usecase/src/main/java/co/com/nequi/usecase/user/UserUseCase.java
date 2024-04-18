package co.com.nequi.usecase.user;

import co.com.nequi.model.config.ErrorCode;
import co.com.nequi.model.config.OnboardingException;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final SingleUserRepository singleUserRepository;
    private final RedisRepository redisRepository;
    private final SQSSenderRepository sqsSenderRepository;
    private final UserDynamoRepository userDynamoRepository;

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
                        .flatMap(userRepository::save)
                        .doOnNext(userSaved -> sqsSenderRepository.sendEvent(userSaved).subscribe()));
    }

    private Mono<User> getSingleUser(Integer id) {
        return singleUserRepository.getSingleUser(id)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000, "There is not a single user")));
    }

    public Mono<User> getUserById(Integer id) {

        return redisRepository.getById(id)
                .switchIfEmpty(userRepository.findById(id)
                        .flatMap(redisRepository::save)
                        .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.B404000, "The user does not exist"))));
    }

    public Flux<User> getUsers() {
        return userRepository.findAll()
                .flatMap(redisRepository::save)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000)));
    }

    public Flux<User> getUsersByFirstName(String value) {
        return userRepository.getUsersByFirstName(value)
                .switchIfEmpty(Mono.error(new OnboardingException(ErrorCode.S204000)));
    }

    public Mono<User> saveUserUpperCase(User user) {
        User userWithUpperCase = User
                .builder()
                .id(user.getId())
                .email(user.getEmail().toUpperCase())
                .firstName(user.getFirstName().toUpperCase())
                .lastName(user.getLastName().toUpperCase())
                .avatar(user.getAvatar().toUpperCase())
                .build();

        return userDynamoRepository.save(userWithUpperCase);
    }

}
