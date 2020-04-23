package com.task.bloomfilter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SpellCheckException.class)
    public final ResponseEntity<Object> handleAllExceptions(SpellCheckException ex) {
        SpellCheckExceptionSchema exceptionResponse =
                new SpellCheckExceptionSchema(
                        ex.getMessage(), ex.getDetails(), ex.getHint());
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
