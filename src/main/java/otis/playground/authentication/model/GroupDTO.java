package otis.playground.authentication.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GroupDTO {
    @JsonView(Views.SignUp.class)
    private String name;

    @JsonView(Views.SignUp.class)
    private Set<RoleDTO> roles;
}
