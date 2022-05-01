package com.techelevator.tenmo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid User ID")
public class InvalidUserId extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidUserId() {
        super("Invalid User ID");
    }
}

