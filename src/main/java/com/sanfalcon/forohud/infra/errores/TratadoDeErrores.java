package com.sanfalcon.forohud.infra.errores;

import com.sanfalcon.forohud.domain.exceptions.ErrorAutenticationUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadoDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratandoError400(MethodArgumentNotValidException e) {
        List<DatosErrorValidacion> errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(ValidacionDeIntegridad e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ValidacionDeIntegridadDatos(
                e.getHttpStatus().value(),
                e.getErrorCode(),
                e.getErrorMessage(),
                e.getRequest().getRequestURI(),
                LocalDateTime.now()));
    }

    private record DatosErrorValidacion(String campo, String error) {
        public DatosErrorValidacion(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> errorCuerpoDeSolicitud(HttpMessageNotReadableException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidacionDeIntegridadDatos(
                HttpStatus.BAD_REQUEST.value(),
                "Bab Request",
                "El cuerpo de la solicitud es invalido",
                "/",
                LocalDateTime.now()));
    }
}
