package com.sanfalcon.forohud.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        String titulo,
        String mensaje,
        String autor,
        String curso,
        Estado estado,
        LocalDateTime fechacreacion
) {
    public DatosDetalleTopico(TopicoEntity topicoEntity) {
        this(topicoEntity.getTitulo(), topicoEntity.getMensaje(), topicoEntity.getAutor(), topicoEntity.getCurso(), topicoEntity.getEstado(), topicoEntity.getFecha());
    }
}
