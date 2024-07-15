package com.sanfalcon.forohud.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopicoAtendido(
        @NotNull(message = "Este campo es obligatorio")
        Long id,
        @NotNull(message = "Este campo es obligatorio")
        Estado estado
) {
}
