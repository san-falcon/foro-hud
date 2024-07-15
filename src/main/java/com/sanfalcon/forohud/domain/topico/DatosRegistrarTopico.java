package com.sanfalcon.forohud.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistrarTopico(
        @NotBlank(message = "Este campo es obligatorio")
        String titulo,
        @NotBlank(message = "Este campo es obligatorio")
        String mensaje,
        @NotBlank(message = "Este campo es obligatorio")
        String autor,
        @NotBlank(message = "Este campo es obligatorio")
        String curso
) {
}
