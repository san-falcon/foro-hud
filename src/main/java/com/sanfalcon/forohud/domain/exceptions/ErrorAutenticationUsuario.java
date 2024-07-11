package com.sanfalcon.forohud.domain.exceptions;

import java.time.LocalDateTime;

public record ErrorAutenticationUsuario(Integer status, String error, String message, String path, LocalDateTime timestamp) {
}
