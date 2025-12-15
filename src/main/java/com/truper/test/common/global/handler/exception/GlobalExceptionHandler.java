package com.truper.test.common.global.handler.exception;

import com.truper.test.common.exception.ItemNotFoundException;
import com.truper.test.common.exception.StandardBadRequestException;
import com.truper.test.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse exceptionHandler(ItemNotFoundException e) {
        return new ErrorResponse(
                LocalDateTime.now(),
                404,
                e.getMessage(),
                new HashMap<>()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors =  new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errors.put(field, field + " " + message);
        });
        return new ErrorResponse(
                LocalDateTime.now(),
                400,
                "Fields error",
                errors
        );
    }

    @ExceptionHandler(StandardBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse exceptionHandler(StandardBadRequestException e) {
        return new ErrorResponse(
                LocalDateTime.now(),
                400,
                e.getMessage(),
                new HashMap<>()
        );
    }
}
