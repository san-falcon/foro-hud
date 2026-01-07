package com.sanfalcon.forohud.domain.usuario;

public record AuthenticationSuccess(
        DatosDetalleUsuario usuario,
        String token
) {
}
