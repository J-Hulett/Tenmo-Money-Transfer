package com.techelevator.tenmo.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;

@ResponseStatus( code = HttpStatus.BAD_REQUEST, reason = "Invalid SQL Input - Will not be Committed to Database")
public class ServerSideSQLError extends SQLException {
    private static final long serialVersionUID = 1L;

    public ServerSideSQLError() {
        super("Invalid SQL Input - Will not be Committed to Database");
    }
}
