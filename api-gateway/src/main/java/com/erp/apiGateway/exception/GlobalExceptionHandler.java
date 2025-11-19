package com.erp.apiGateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception ex) {
//        ApiError apiError = new ApiError();
//        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        apiError.setMessage(ex.getMessage());
//        apiError.setDebugMessage(ex.getLocalizedMessage());
//        apiError.setCode(INTERNAL_SERVER_ERROR.getErrorCode());
//        apiError.setTimestamp(clock.millis());
//        logger.logError(ex.getMessage(), ex);
        return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
