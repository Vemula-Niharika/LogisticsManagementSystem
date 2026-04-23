package com.alpha.logistics.exception;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.alpha.logistics.dto.ResponseStructure;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // NOT FOUND (404)
    @ExceptionHandler({
            AddressNotFoundException.class,
            CargoNotFoundException.class,
            DriverNotFoundException.class,
            TruckNotFoundException.class,
            OrderNotFoundException.class
    })
    public ResponseEntity<ResponseStructure<Object>> handleNotFound(RuntimeException ex) {
        ResponseStructure<Object> response =
                new ResponseStructure<>(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // DUPLICATE (409)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseStructure<Object>> handleDuplicate(DuplicateResourceException ex) {
        ResponseStructure<Object> response =
                new ResponseStructure<>(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // VALIDATION ERROR (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        ResponseStructure<Map<String, String>> response =
                new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // ENUM / BAD INPUT (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseStructure<Object>> handleIllegalArgument(IllegalArgumentException ex) {
        ResponseStructure<Object> response =
                new ResponseStructure<>(HttpStatus.BAD_REQUEST.value(), "Invalid input: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // GENERAL ERROR (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<Object>> handleGeneral(Exception ex) {
        ResponseStructure<Object> response =
                new ResponseStructure<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong: " + ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}