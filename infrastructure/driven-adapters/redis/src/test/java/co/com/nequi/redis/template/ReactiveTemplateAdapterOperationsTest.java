package co.com.nequi.redis.template;

import co.com.nequi.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ReactiveRedisTemplateAdapterOperationsTest {

    @Mock
    private ReactiveRedisConnectionFactory connectionFactory;

    @Mock
    private ObjectMapper objectMapper;

    private ReactiveRedisTemplateAdapter adapter;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(objectMapper.map("value", Object.class)).thenReturn("value");

        adapter = new ReactiveRedisTemplateAdapter(connectionFactory, objectMapper);

        user = User.builder()
                .id(1)
                .email("camilo@correo.com")
                .firstName("Camilo")
                .lastName("Monje")
                .avatar("http://123.com")
                .build();
    }

    @Test
    void testFindById() {

        StepVerifier.create(adapter.findById(1))
                .verifyComplete();
    }

}