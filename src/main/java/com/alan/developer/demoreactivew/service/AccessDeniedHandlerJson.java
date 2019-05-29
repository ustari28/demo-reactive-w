package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.GenericError;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Log
@RestControllerAdvice
public class AccessDeniedHandlerJson {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<GenericError> expiredJwtException(ExpiredJwtException ex, WebRequest wr) {
        log.info("Expired");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericError.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message(ex.getMessage())
                        .description(ex.getLocalizedMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericError> all(Exception ex, WebRequest wr) {
        log.info("Exception");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericError.builder().message(ex.getMessage())
                        .description(ex.getLocalizedMessage()).build());
    }
/**
 @GetMapping("/error") public GenericError unAuthorized403(HttpServletRequest req, HttpServletResponse res) {
 //req.geta
 return GenericError.builder().message("Unauthorized user")
 .status(res.getStatus())
 .description("sxxxx").build();
 }
 */
}
