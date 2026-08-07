package com.example.smartdispensa.exception;

import com.example.smartdispensa.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;




import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            ValidationErrorDTO dto = new ValidationErrorDTO(error.getField(), error.getDefaultMessage());
            errors.add(dto);
        }
        return ResponseEntity.badRequest().body(errors);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex){
        String field = "json";
        String message = "O corpo da requisição possui um erro de sintaxe ou formato inválido.";
        List<ValidationErrorDTO> errors = new ArrayList<>();

        if (ex.getMessage() != null) {
            message = "O valor enviado está com o tipo de dado ou formato incorreto para algum campo (ex: texto em número ou data inválida).";
        }

        ValidationErrorDTO dto = new ValidationErrorDTO(field, message);
        errors.add(dto);

        return ResponseEntity.badRequest().body(errors);
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<List<ValidationErrorDTO>> handleResourceNotFound(ResourceNotFoundException ex) {
        List<ValidationErrorDTO> errors = new ArrayList<>();

        String field = "id";
        String message = ex.getMessage();

        ValidationErrorDTO dto = new ValidationErrorDTO(field, message);
        errors.add(dto);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

}
