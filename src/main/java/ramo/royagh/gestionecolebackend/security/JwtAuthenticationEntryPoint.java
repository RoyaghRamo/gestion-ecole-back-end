package ramo.royagh.gestionecolebackend.security;

// Step 6: Working on the Authentication Entry Point
// Authentication Entry Point is an interface that provides the
// implementation for a method called comments.
// This is called whenever an exception is thrown,
// because a user is trying to access our resource that requires authentication.

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ramo.royagh.gestionecolebackend.exceptions.InvalidLoginResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // If you do not put this annotation, you won't be able to autowire it in the SecurityConfig class
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This is how we're going to manage the errors thrown whenever an
    // user tries to access our resource without being authenticated
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        // Step 8: Refactor the errors thrown
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);



    }
}
