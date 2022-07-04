package com.interview.music.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Sandeep on 7/4/2022
 */
@Data
public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 4717247731899872575L;

    private String reason;
    private HttpStatus httpStatus;
    private String txnTimestamp;

    public ApiException() {
    }

    public ApiException(Throwable cause, String reason, HttpStatus httpStatus) {
        super(reason, cause);
        this.reason = reason;
        this.httpStatus = httpStatus;
        this.txnTimestamp = LocalDateTime.now().toString();
    }

}
