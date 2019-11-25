package ramo.royagh.gestionecolebackend.security;


// Abstracting values
public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/gestion-ecole/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";

    // The Token_Prefix must have a space after. It's really important:
    //
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 18000_000 ; // in ms

}
