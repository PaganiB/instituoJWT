package com.security.Instituto.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.jwt.private.key}")
    private String privateKey;

    @Value("${security.jwt.user.generator")
    private String userGenerator;

    //metodo para crear y encriptar token
    public String createToken (Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();

        //se traen los permisos separados por coma
        String authorities = authentication.getAuthorities()
                //el metodo .stream se llama en el objeto Collection authentication.getAuthorized() para convertir la coleccion de autoridades en un Stream
                //(secuencia de elementos que permite realizar operaciones de forma secuencial o paralela)
                .stream()
                //se aplica la operacion map en el metodo stream.
                // En este caso se esta llamando al metodo getAuthority de cada objeto GrantedAuthority en el Stream
                //se transforma cada objeto GrantedAuthority en su autoridad representado como una cadena de texto
                .map(GrantedAuthority::getAuthority)
                //se usa collect para combinar todos los elementos del Stream en una sola cadena.
                //el metodo Collectors.joining concatena los elementos usando la coma como delimitador
                .collect(Collectors.joining(","));

        //a partir de esto se genera el token
        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)//aqui va el usuario que genera el token
                .withSubject(username)//a quien se le genera el token
                .withClaim("authorities", authorities)//claims del token
                .withIssuedAt(new Date())//fecha de creacion del token
                .withExpiresAt(new Date(System.currentTimeMillis()+ 1800000)) //fecha de expiracion del token
                .withJWTId(UUID.randomUUID().toString())//id del token (randomizado)
                .withNotBefore(new Date(System.currentTimeMillis()))// desde cuando es válido. En este caso desde el momento creado
                .sign(algorithm); //firma del token creada con la privateKey

        return jwtToken;
    }

    //metodo para validar y decodificar tokens
    public DecodedJWT validateTokens(String token){
        try{
            //se usa el metodo Algorithm para encriptar el token
            //creando un Algorithm utilizando HMAC con SHA-256 y la clave privada.
            //con la clave se firma el token y se lo verifica
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey); //algoritmo de cifrado + clave privada
            //se crea un objeto JWTVerifier que usa el algoritmo previamente creado para verificar el token
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)//se especifica el emisor del token
                    .build();//se finaliza la construccion del verificador JWT

            //si está bien no se generan exceptciones y se hace el return
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        }
        catch (JWTVerificationException exception){
            throw new JWTVerificationException("Token inválido. No autorizado");
        }
    }

    //metodo para obtener el nombre de usuario del token
    public String extractUsername(DecodedJWT decodedJWT){
        //el subject es el usuario segun establecimos al crear el token
        return decodedJWT.getSubject().toString();
    }

    //metodos para obtener los claims

    //para obtener uno especifico
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    //para obtener todos los claims
    public Map<String , Claim> returnAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
