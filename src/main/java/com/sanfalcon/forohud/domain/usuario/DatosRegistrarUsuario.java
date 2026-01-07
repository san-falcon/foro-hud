package com.sanfalcon.forohud.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosRegistrarUsuario(
        @NotBlank(message = "Este campo es obligatorio")
        String nombre,
        @NotBlank(message = "Este campo es obligatorio")
        @Email(message = "Formato del correo invalido")
        String correo,
        @NotBlank(message = "Este campo es obligatorio")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
                message = "La contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula y un número."
        )
        String password

) {
}
