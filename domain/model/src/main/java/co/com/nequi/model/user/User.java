package co.com.nequi.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class User {

    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;


}
