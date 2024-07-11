package com.sanfalcon.forohud.infra.errores;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidacionDeIntegridad extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private HttpServletRequest request;
    private HttpStatus httpStatus;


    public ValidacionDeIntegridad(String errorCode, String errorMessage, HttpServletRequest request, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.request = request;
        this.httpStatus = httpStatus;
    }
}
