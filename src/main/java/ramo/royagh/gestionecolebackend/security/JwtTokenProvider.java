package ramo.royagh.gestionecolebackend.security;

import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ramo.royagh.gestionecolebackend.entities.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static ramo.royagh.gestionecolebackend.security.SecurityConstants.EXPIRATION_TIME;
import static ramo.royagh.gestionecolebackend.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    // Generate the token
    public String generateToken(Authentication authentication){

        // Extract the user that's authenticated

        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        Date expireDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        // Informations we want to include in our tokens
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("nom", user.getNom());
        claims.put("prenom", user.getPrenom());
        // We might want to throw the roles in here too

        return Jwts.builder()
                .setSubject(userId)
                // Claims are informations about the the user
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // Validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException e){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException e){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException e){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException e){
            System.out.println("JWT claims String is empty");
        }
        return false;
    }

    // Get the user Id from the token
    public Long getUserIdFromJWT(String token){

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);


    }

}
