package com.sanfalcon.forohud.domain.usuario;

public record DatosDetalleUsuario(
        Long id,
        String nombre,
        String correo
) {
    public DatosDetalleUsuario(UsuarioEntity usuarioEntity) {
        this(usuarioEntity.getId(), usuarioEntity.getNombre(), usuarioEntity.getCorreo());
    }
}
