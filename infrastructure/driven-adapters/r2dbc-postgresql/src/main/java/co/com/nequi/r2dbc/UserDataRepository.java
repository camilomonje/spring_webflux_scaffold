package co.com.nequi.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserDataRepository extends R2dbcRepository<UserData, Integer> {

    Mono<UserData> findByEmail(String email);

}
