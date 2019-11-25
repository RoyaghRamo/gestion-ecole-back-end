package ramo.royagh.gestionecolebackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ramo.royagh.gestionecolebackend.entities.User;
import ramo.royagh.gestionecolebackend.services.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static ramo.royagh.gestionecolebackend.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private String getJWTFromRequest(HttpServletRequest httpServletRequest){

        String bearerToken = httpServletRequest.getHeader(HEADER_STRING);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {

            // Grab the JWT from the our request, in this case HttpServletRequest
            String jwt = getJWTFromRequest(httpServletRequest);

            // Make sure that the token we just extracted from the request
            // isn't null and
            // has text and
            // that is valid

            if(StringUtils.hasText(jwt) /* Make sure it's not null */
                    &&
                    jwtTokenProvider.validateToken(jwt) /* Make sure it's valid */){

                // Grab the userId from the jwt token
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                // Grab the user Details by the userId
                User userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList()
                );

                authentication .setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }catch (Exception e){
            logger.error("Could not set user authentication in security context", e);
        }

        // Without this line of code, it won't work
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}
