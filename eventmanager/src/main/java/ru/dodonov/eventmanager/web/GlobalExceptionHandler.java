package ru.dodonov.eventmanager.web;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ServerMessageError> handleException(Exception e) {
        log.error("Handle common exception", e);
        var messageResponse = new ServerMessageError(
                "Server error",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(500)
                .body(messageResponse);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ServerMessageError> handleBadRequest(Exception e) {
        log.error("Handle bad request", e);
        ServerMessageError messageResponse = new ServerMessageError(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400)
                .body(messageResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ServerMessageError> handleNotFound(Exception e) {
        log.error("Entity not found", e);
        ServerMessageError messageResponse = new ServerMessageError(
                "Bad request",
                e.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(404)
                .body(messageResponse);
    }
}
