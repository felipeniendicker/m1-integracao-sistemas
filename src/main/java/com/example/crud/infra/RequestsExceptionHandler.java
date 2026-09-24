package com.example.crud.infra;

import com.example.crud.domain.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestsExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleProductNotFound(ProductNotFoundException exception) {
        ExceptionDTO response = new ExceptionDTO(exception.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleValidationError(MethodArgumentNotValidException exception) {
        String message = "Invalid product data";

        if (exception.getFieldError() != null) {
            message = exception.getFieldError().getField() + ": "
                    + exception.getFieldError().getDefaultMessage();
        }

        ExceptionDTO response = new ExceptionDTO(message, 400);
        return ResponseEntity.badRequest().body(response);
    }
}
