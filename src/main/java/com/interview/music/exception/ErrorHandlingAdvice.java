package com.interview.music.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class ErrorHandlingAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    final ApiError onConstraintValidationException(final ConstraintViolationException e) {
        log.error("Bad Request ", e);
        final StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(e.getConstraintViolations())) {
            e.getConstraintViolations().forEach(violationError -> {
                sb.append(violationError.getPropertyPath().toString());
                sb.append(":");
                sb.append(violationError.getMessage());
                sb.append("; ");
            });
        }
        return new ApiError(sb.toString());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    final ApiError onMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("Bad Request ", e);
        final StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(e.getBindingResult().getFieldErrors())) {
            e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                sb.append(fieldError.getField());
                sb.append(":");
                sb.append(fieldError.getDefaultMessage());
                sb.append("; ");
            });
        }
        return new ApiError(sb.toString());
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    final ApiError onJsonParseException(final JsonMappingException e) {
        log.error("Bad Request ", e);
        final StringBuilder sb = new StringBuilder();
        for (JsonMappingException.Reference reference : e.getPath()) {
            sb.append(reference.getFieldName());
            sb.append(":");
            sb.append(e.getCause().getLocalizedMessage());
            sb.append("; ");
        }
        return new ApiError(sb.toString());

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    final ApiError onHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error("Bad Request ", e);
        return new ApiError(e.getMessage());
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    final ResponseEntity<ApiError> onSSFException(final ApiException e) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                e.getTxnTimestamp()
        );
        log.error("Api ApiError:: {}", apiError, e);
        return new ResponseEntity<>(apiError, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    final ResponseEntity<ApiError> onException(final Exception e) {
        ApiError apiError = new ApiError(
                e.getMessage(),
                "Something went wrong"
        );
        log.error("ApiError:{}", apiError, e);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
