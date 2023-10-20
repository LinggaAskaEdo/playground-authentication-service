package otis.playground.authentication.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Request {
    @JsonView({ Views.SignIn.class, Views.SignUp.class })
    private String username;

    @JsonView({ Views.SignIn.class, Views.SignUp.class })
    private String password;

    @JsonView(Views.SignUp.class)
    private String email;

    @JsonView(Views.SignUp.class)
    private GroupDTO group;
}
