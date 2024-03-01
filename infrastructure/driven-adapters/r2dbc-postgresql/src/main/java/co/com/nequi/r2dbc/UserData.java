package co.com.nequi.r2dbc;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("users")
public class UserData {

    @Id
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

}
