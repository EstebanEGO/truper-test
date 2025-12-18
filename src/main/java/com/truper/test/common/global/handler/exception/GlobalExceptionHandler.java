package com.truper.test.common.global.handler.exception;

import com.truper.test.common.exception.ItemNotFoundException;
import com.truper.test.common.exception.StandardBadRequestException;
import com.truper.test.dto.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail exceptionHandler(ItemNotFoundException e) {
        return new ErrorResponse
                .Builder()
                .statusCode(404)
                .detail(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail exceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors =  new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, field + " " + message);
        });
        return new ErrorResponse
                .Builder()
                .statusCode(400)
                .detail("Fields error")
                .addProperty("errors", errors)
                .build();
    }

    @ExceptionHandler(StandardBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail exceptionHandler(StandardBadRequestException e) {
        return new ErrorResponse
                .Builder()
                .statusCode(400)
                .detail(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        int statusCode = 500;
        String detail = "Unknown internal server error.";
        String error = exception.getMessage();


        if (exception instanceof BadCredentialsException) {
            statusCode = 401;
            detail = "The username or password is incorrect";
        }

        if (
                exception instanceof AccountStatusException ||
                        exception instanceof AccessDeniedException ||
                        exception instanceof SignatureException ||
                        exception instanceof ExpiredJwtException
        ) {
            statusCode = 403;
            detail = switch (exception) {
                case AccountStatusException ignored -> "The account is locked";
                case AccessDeniedException ignored -> "You are not authorized to access this resource";
                case SignatureException ignored -> "The JWT signature is invalid";
                case ExpiredJwtException ignored -> "The JWT token has expired";
                default -> detail;
            };
        }


        return new ErrorResponse
                .Builder()
                .statusCode(statusCode)
                .detail(detail)
                .addProperty("error", error)
                .build();
    }
}
