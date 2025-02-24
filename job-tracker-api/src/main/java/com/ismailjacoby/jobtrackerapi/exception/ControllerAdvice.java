package com.ismailjacoby.jobtrackerapi.exception;

import com.ismailjacoby.jobtrackerapi.dto.ErrorDTO;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest req) {
        logger.error("Entity not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage(), LocalDateTime.now(), req.getRequestURI()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest req) {
        logger.error("Illegal argument: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(ex.getMessage(), LocalDateTime.now(), req.getRequestURI()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest req) {
        List<String> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> errors.add(violation.getMessage()));
        logger.error("Constraint violation: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(errors.toString(), LocalDateTime.now(), req.getRequestURI()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        logger.error("Not Found Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage(), LocalDateTime.now(), req.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex, HttpServletRequest req) {
        logger.error("An unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO("An unexpected error occurred", LocalDateTime.now(), req.getRequestURI()));
    }

    @ExceptionHandler(AccountNotActiveException.class)
    public ResponseEntity<ErrorDTO> handleAccountNotActiveException(AccountNotActiveException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDTO(
                ex.getMessage(),
                LocalDateTime.now(),
                req.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error instanceof FieldError
                    ? String.format("Field %s: %s", ((FieldError) error).getField(), error.getDefaultMessage())
                    : error.getDefaultMessage();
            errors.add(errorMessage);
        });
        logger.error("Validation errors: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(errors.toString(), LocalDateTime.now(), req.getRequestURI()));
    }
}