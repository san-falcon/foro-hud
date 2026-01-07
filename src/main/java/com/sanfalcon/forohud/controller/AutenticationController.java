package com.sanfalcon.forohud.controller;

import com.sanfalcon.forohud.domain.usuario.*;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            var usuario = (UsuarioEntity) usuarioAutenticado.getPrincipal();

            var datosDetalleUsuario = new DatosDetalleUsuario(usuario);

            return ResponseEntity.ok(new AuthenticationSuccess(datosDetalleUsuario, JWTtoken));
        } catch (BadCredentialsException e) {
            throw new ValidacionDeIntegridad("Unauthorized", "Correo o contraseña incorrecta", request, HttpStatus.UNAUTHORIZED);
        } catch (AuthenticationException e) {
            throw new ValidacionDeIntegridad("Forbidden", "Autenticación fallida", request, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/check-status")
    public ResponseEntity<DatosCheckStatus> checkStatus(
            @AuthenticationPrincipal UsuarioEntity usuarioEntity
    ) {
        String nuevoToken = this.jwtUtilsService.generarToken(usuarioEntity);
        DatosDetalleUsuario datosDetalleUsuario = new DatosDetalleUsuario((usuarioEntity));
        DatosCheckStatus datosCheckStatus = new DatosCheckStatus(datosDetalleUsuario, nuevoToken);
        return ResponseEntity.ok(datosCheckStatus);
    }
}
