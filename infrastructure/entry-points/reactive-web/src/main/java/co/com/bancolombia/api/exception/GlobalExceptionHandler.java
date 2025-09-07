package co.com.bancolombia.api.exception;

import co.com.bancolombia.api.dto.ErrorResponse;
import co.com.bancolombia.model.exception.UnauthorizedActionException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo genérico de cualquier excepción
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleException(Exception ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Error interno en el sistema",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );
        return Mono.just(ResponseEntity.status(status).body(error));
    }

    // Manejo de errores de validación con @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleValidationException(MethodArgumentNotValidException ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = ex.getBindingResult().getFieldError() != null
                ? ex.getBindingResult().getFieldError().getDefaultMessage()
                : "Fallo la validación";

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Error en la validación",
                message,
                exchange.getRequest().getURI().getPath()
        );
        return Mono.just(ResponseEntity.status(status).body(error));
    }

    // Manejo de errores por violación de restricciones
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleConstraintViolationException(ConstraintViolationException ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Violación de restricción",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );
        return Mono.just(ResponseEntity.status(status).body(error));
    }

    // Manejo de errores por parámetros inválidos
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "Parámetros inválidos",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );
        return Mono.just(ResponseEntity.status(status).body(error));
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleUnauthorizedActionException(UnauthorizedActionException ex, ServerWebExchange exchange) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                "No permitido",
                ex.getMessage(),
                exchange.getRequest().getURI().getPath()
        );
        return Mono.just(ResponseEntity.status(status).body(error));
    }
}
