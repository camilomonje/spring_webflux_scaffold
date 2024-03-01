package co.com.nequi.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserDataRepository extends R2dbcRepository<UserData, Integer> {

    Mono<UserData> findByEmail(String email);

    @Query("SELECT * " +
            "FROM users " +
            "WHERE LOWER(first_name) LIKE LOWER('%' || :value || '%')")
    Flux<UserData> findUsersByFirstName(String value);

}
