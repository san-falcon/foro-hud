package com.sanfalcon.forohud.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosLoginUsuario(
        @NotBlank(message = "Este campo es obligatorio")
        @Email(message = "Formato del correo invalido")
        String correo,
        @NotBlank(message = "Este campo es obligatorio")
        @Pattern(regexp = "^.{8,}$", message = "La contraseña debe tener minimo 8 caracteres")
        String password
) {
}
