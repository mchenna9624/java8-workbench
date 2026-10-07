package com.example.workbench.web;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity notFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("not_found", exception.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity badRequest(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("bad_request", exception.getMessage()));
    }

    private Map error(String code, String message) {
        Map body = new HashMap();
        body.put("code", code);
        body.put("message", message);
        return body;
    }
}
