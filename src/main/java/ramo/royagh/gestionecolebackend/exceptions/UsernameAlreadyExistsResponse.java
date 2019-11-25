package ramo.royagh.gestionecolebackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UsernameAlreadyExistsResponse {

    private String username;

}
