package otis.playground.authentication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Response {
    private int status;
    private String message;
    private String description;
}
