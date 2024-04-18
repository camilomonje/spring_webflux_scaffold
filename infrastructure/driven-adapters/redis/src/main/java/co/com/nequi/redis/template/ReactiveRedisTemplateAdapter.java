package co.com.nequi.redis.template;

import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.RedisRepository;
import co.com.nequi.redis.template.helper.ReactiveTemplateAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactiveRedisTemplateAdapter extends ReactiveTemplateAdapterOperations<User, Integer, UserRedis>
  implements RedisRepository
{
    public ReactiveRedisTemplateAdapter(ReactiveRedisConnectionFactory connectionFactory, ObjectMapper mapper) {

        super(connectionFactory, mapper, d -> mapper.map(d, User.class));
    }

    @Override
    public Mono<User> save(User user) {
        return save(user.getId(), user);
    }

    @Override
    public Mono<User> getById(Integer id) {
        return findById(id);
    }
}
