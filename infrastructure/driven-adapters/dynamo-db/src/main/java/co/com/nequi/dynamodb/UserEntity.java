package co.com.nequi.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Setter
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Integer id;
    @Getter
    private String email;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    private String avatar;

    @DynamoDbPartitionKey
    public Integer getId() {
        return id;
    }

}
