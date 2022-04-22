package com.iaccessvision.exam.whitelistservice.exception;

import com.iaccessvision.exam.whitelistservice.dto.HttpErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WhitelistingException.class)
    public ResponseEntity<HttpErrorResponse> handleWhitelistingException(WhitelistingException exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpErrorResponse errorResponse = new HttpErrorResponse();
        errorResponse.setCode(400);
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURI());

        return ResponseEntity.badRequest()
                .body(errorResponse);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpErrorResponse errorResponse = new HttpErrorResponse();
        errorResponse.setCode(404);
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURI());

        return new ResponseEntity<HttpErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpErrorResponse errorResponse = new HttpErrorResponse();
        errorResponse.setCode(400);
        errorResponse.setMessage("The given data was invalid.");
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setPath(servletWebRequest.getRequest().getRequestURI());

        Map<String, String> errors = new LinkedHashMap<>();

       ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .forEach(fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
       errorResponse.setErrors(errors);

        return ResponseEntity.badRequest()
                .body(errorResponse);
    }
}
