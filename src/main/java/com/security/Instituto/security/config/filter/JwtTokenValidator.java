package com.security.Instituto.security.config.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.Instituto.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(jwtToken != null){
            //en el header antes del token se encuentra la palabra 'bearer' por lo que debemos sacarlo
            jwtToken = jwtToken.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateTokens(jwtToken);

            //si el token está bien, le damos acceso
            String username = jwtUtils.extractUsername(decodedJWT);
            //me devuelve claim, necesito pasarlo a String
            String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();
            //si está bien, hay que setearlo en el Context Holder primero convirtiendolo a GrantedAutority
            Collection authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
            //si se valida el token, se le da acceso al usuario en el Context Holder
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }
        //si el token esta mal
        filterChain.doFilter(request, response);
    }
}
