package com.interview.music.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Sandeep on 7/4/2022
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"code", "reason", "message", "transactionTimestamp", "id"})
@ApiResponse
public class ApiError {
    private final String message;
    private final String transactionTimestamp;

    public ApiError(String message) {
        this.message = message;
        this.transactionTimestamp = LocalDateTime.now().toString();
    }

    public ApiError(String message, String transactionTimestamp) {
        this.message = message;
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }


    @Override
    public String toString() {
        return "ApiError{" +
                ", message='" + message + '\'' +
                ", transactionTimestamp='" + transactionTimestamp + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiError)) return false;
        ApiError apiError = (ApiError) o;
        return Objects.equals(message, apiError.message) &&
                transactionTimestamp.equals(apiError.transactionTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, transactionTimestamp);
    }

}
