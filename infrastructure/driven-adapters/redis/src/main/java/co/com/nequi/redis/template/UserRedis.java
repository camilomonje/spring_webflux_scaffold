package co.com.nequi.redis.template;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRedis {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;


}
