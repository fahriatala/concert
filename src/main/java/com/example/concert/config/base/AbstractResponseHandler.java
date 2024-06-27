package com.example.concert.config.base;

import com.example.concert.config.exception.AppException;
import com.example.concert.config.exception.DataNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractResponseHandler {
    protected <T> ResponseEntity<ResultResponse<T>> generateResponse(T data, HttpStatus status, String message) {
        MetaResponse metaResponse = new MetaResponse(status.value(), message);
        ResultResponse<T> result = new ResultResponse<>(status.is2xxSuccessful() ? "OK" : "ERROR", data, metaResponse);
        return new ResponseEntity<>(result, createHttpHeaders(), status);
    }

    protected <T> ResponseEntity<ResultResponse<T>> generateErrorResponse(Exception ex, HttpStatus status, String message) {
        String debugInfo = ex.getMessage();
        MetaResponse metaResponse = new MetaResponse(status.value(), message, debugInfo);
        ResultResponse<T> result = new ResultResponse<>("ERROR", null, metaResponse);
        return new ResponseEntity<>(result, createHttpHeaders(), status);
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ResultResponse<T>> handleException(Exception ex) {
        HttpStatus status;
        if (ex instanceof DataNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof AppException) {
            status = HttpStatus.BAD_REQUEST;
        } else  {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return generateErrorResponse(ex, status, ex.getMessage());
    }
}
