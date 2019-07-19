package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class InvalidClientIdException extends AppException {

    public InvalidClientIdException() {
        super(404, Response.Status.BAD_REQUEST.getStatusCode(), "Invalid client id", "", "");
    }
}
