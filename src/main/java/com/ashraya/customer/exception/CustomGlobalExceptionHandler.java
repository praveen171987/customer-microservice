package com.ashraya.customer.exception;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);
    
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
        logger.error("Error =======> " + ex.getMessage());       
        CustomErrorResponse errors = createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> customErrorHandle(Exception ex, WebRequest request) {
        logger.error("Error =======> ", ex);
        String message = ex.getMessage()==null ? "null value found" : ex.getMessage();
        CustomErrorResponse errors = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public CustomErrorResponse createErrorResponse(HttpStatus httpStatus, String message) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        errors.setError(message);
        errors.setStatus(httpStatus.value());
        return errors;
    }
}
