package com.sanfalcon.forohud.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosActualizarTopico(
        @NotBlank(message = "Este campo es obligatorio")
        String titulo,
        @NotBlank(message = "Este campo es obligatorio")
        String mensaje,
        @NotBlank(message = "Este campo es obligatorio")
        String autor,
        @NotBlank(message = "Este campo es obligatorio")
        String curso
) {
    public DatosActualizarTopico(TopicoEntity topicoEntity) {
        this(topicoEntity.getTitulo(), topicoEntity.getMensaje(), topicoEntity.getAutor(), topicoEntity.getCurso());
    }
}
