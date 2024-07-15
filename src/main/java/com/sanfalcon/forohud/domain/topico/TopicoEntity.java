package com.sanfalcon.forohud.domain.topico;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "topicos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private String autor;

    private String curso;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    public void actualizaDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.autor() != null) {
            this.autor = datos.autor();
        }
        if (datos.curso() != null) {
            this.curso = datos.curso();
        }
    }

    public void actualizaAtentido(DatosActualizarTopicoAtendido datos) {
        if (datos.estado() != null) {
            this.estado = datos.estado();
        }
    }
}
