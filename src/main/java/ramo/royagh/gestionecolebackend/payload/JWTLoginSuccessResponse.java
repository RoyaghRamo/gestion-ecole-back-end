package ramo.royagh.gestionecolebackend.payload;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@ToString
public class JWTLoginSuccessResponse {

    private boolean success;

    private String token;

}
