package com.sanfalcon.forohud.controller;

import com.sanfalcon.forohud.domain.usuario.*;
import com.sanfalcon.forohud.infra.security.jwt.JwtUtilsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtilsService jwtUtilsService;

    @PostMapping("/crear")
    public ResponseEntity crearUsuario(@RequestBody @Valid DatosRegistrarUsuario datos, UriComponentsBuilder uriComponentsBuilder) {
        UsuarioEntity usuario = UsuarioEntity.builder()
                .nombre(datos.nombre())
                .correo(datos.correo())
                .password(passwordEncoder.encode(datos.password())).build();
        usuarioRepository.save(usuario);

        String token = this.jwtUtilsService.generarToken(usuario);

        URI uri = uriComponentsBuilder.path("/usuario/detalle/{id}").buildAndExpand(usuario.getId()).toUri();

        DatosDetalleUsuario datosDetalleUsuario = new DatosDetalleUsuario(usuario);
        AuthenticationSuccess datosCheckStatus = new AuthenticationSuccess(datosDetalleUsuario, token);

        return ResponseEntity.created(uri).body(datosCheckStatus);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity verDetalleUsuario(@PathVariable Long id) {
        Optional<UsuarioEntity> usuarioEntity = usuarioRepository.findById(id);
        if (!usuarioEntity.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DatosDetalleUsuario(usuarioEntity.get()));
    }
}
