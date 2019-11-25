package ramo.royagh.gestionecolebackend.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Le nom d'utilisateur ne doit pas être vide!")
    private String username;

    @NotBlank(message = "Le mot de passe ne doit pas être vide!")
    private String password;

}
