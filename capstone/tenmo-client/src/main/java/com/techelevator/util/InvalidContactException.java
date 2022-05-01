package com.techelevator.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid UserID or Contact Selected")
public class InvalidContactException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidContactException() {
        super("Invalid UserID or Contact Selected");
    }
}
