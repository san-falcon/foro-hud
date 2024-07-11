package com.sanfalcon.forohud.infra.errores;

import java.time.LocalDateTime;

public record ValidacionDeIntegridadDatos(
        Integer status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
) {
}
