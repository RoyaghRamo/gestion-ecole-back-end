package ramo.royagh.gestionecolebackend.exceptions;

// Step 7: Creating the Invalid Login Response to add later in the SecurityConfig class

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }
}
