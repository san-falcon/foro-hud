package com.sanfalcon.forohud.infra.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sanfalcon.forohud.domain.usuario.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

//? Metodos necesarios para trabajar con el token
@Service
public class JwtUtilsService {

    /*?
        Sirve para firmar nuestros tokens.
        La firma asegura que nosotros generamos el token de nuestro usuario
     */
    @Value("${jwt.secret.key}")
    private String secretKey;

    //? Generar token de acceso
    public String generarToken(UsuarioEntity usuarioEntity) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey); //? Se vuelve a encriptar nuestra firma "this.secretKey"
            return JWT.create()
                    .withIssuer("foro hud")
                    .withSubject(usuarioEntity.getCorreo())
                    .withClaim("id", usuarioEntity.getId())
                    .withExpiresAt(generarFechaDeExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    //? Sirve para dar un tiempo de expiracion a nuestro token. El tiempo se configura con milisegundos
    private Instant generarFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-05:00"));
    }

    //? Aca se valida si el token es valido
    public String getSubject(String token) {
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey); //? Validar la firma
            verifier = JWT.require(algorithm)
                    .withIssuer("foro hud")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("No se pudo verificar porque el getSubject() es null");
        }
        return verifier.getSubject();
    }

}
