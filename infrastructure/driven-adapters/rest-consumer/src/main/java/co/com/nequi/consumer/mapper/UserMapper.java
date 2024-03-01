package co.com.nequi.consumer.mapper;

import co.com.nequi.consumer.ObjectResponse;
import co.com.nequi.model.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toDomain(ObjectResponse objectResponse) {
        return User.builder()
                .email(objectResponse.getData().getEmail())
                .firstName(objectResponse.getData().getFirstName())
                .lastName(objectResponse.getData().getLastName())
                .avatar(objectResponse.getData().getAvatar())
                .build();
    }
}
