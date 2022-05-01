package com.techelevator.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Something bad happened when looking for a username")
public class LookingForUserNameException extends SQLException {
    private static final long serialVersionUID = 1L;

    public LookingForUserNameException(){
        super("Something bad happened when looking for a username.");
    }
}
