package com.challengehub.forohub.utilidades;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.challengehub.forohub.DTOs.DtoError;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ManejoDeErrores
{
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404()
    {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){

        DtoError dtoError = new DtoError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dtoError);
    }
    
    private record DataValidationError(String variable, String message)
    {
        public  DataValidationError(FieldError error)
        {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e)

    {
        var errorsBadRequest = e.getFieldErrors().stream()
                .map(DataValidationError::new).toList();

        return ResponseEntity.badRequest().body(errorsBadRequest);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity returnNullPointer(MethodArgumentNotValidException e)
    {
        DtoError dtoError = new DtoError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dtoError);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity returnIllegalStateException(MethodArgumentNotValidException e)
    {
        DtoError dtoError = new DtoError("404",
                e.getMessage());

        return ResponseEntity.badRequest().body(dtoError);
    }

}