package com.sanfalcon.forohud.controller;

import com.sanfalcon.forohud.domain.usuario.DatosLoginUsuario;
import com.sanfalcon.forohud.domain.usuario.UsuarioEntity;
import com.sanfalcon.forohud.infra.errores.ValidacionDeIntegridad;
import com.sanfalcon.forohud.infra.security.DatosJWTToken;
import com.sanfalcon.forohud.infra.security.jwt.JwtUtilsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autenticacion")
public class AutenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilsService jwtUtilsService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosLoginUsuario datos, HttpServletRequest request) {

        try {
            //? Creacion de los datos que se van a firmar
            Authentication datoaAFirmar = new UsernamePasswordAuthenticationToken(datos.correo(), datos.password());

            var usuarioAutenticado = this.authenticationManager.authenticate(datoaAFirmar);

            var JWTtoken = jwtUtilsService.generarToken((UsuarioEntity) usuarioAutenticado.getPrincipal());
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (BadCredentialsException e) {
            throw new ValidacionDeIntegridad("Unauthorized", "Correo o contraseña incorrecta", request, HttpStatus.UNAUTHORIZED);
        }catch (AuthenticationException e) {
            throw new ValidacionDeIntegridad("Forbidden", "Autenticación fallida", request, HttpStatus.FORBIDDEN);
        }

    }
}
