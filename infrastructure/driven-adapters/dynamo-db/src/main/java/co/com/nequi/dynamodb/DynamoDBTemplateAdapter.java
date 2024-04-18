package co.com.nequi.dynamodb;

import co.com.nequi.dynamodb.helper.TemplateAdapterOperations;
import co.com.nequi.model.user.User;
import co.com.nequi.model.user.gateways.UserDynamoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<User, Integer, UserEntity>
    implements UserDynamoRepository
{

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, User.class), "Users");
    }


}
