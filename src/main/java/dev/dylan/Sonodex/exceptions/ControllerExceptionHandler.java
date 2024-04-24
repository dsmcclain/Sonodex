package dev.dylan.Sonodex.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {JsonProcessingException.class})
    protected ResponseEntity<?> handleJsonError(JsonProcessingException ex, WebRequest request) {
        return ResponseEntity.status(422).body(ex);
    }
}
