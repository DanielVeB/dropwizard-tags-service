package com.comarch.danielkurosz.exceptions;

import javax.ws.rs.core.Response;

public class DuplicateIdException extends AppException {

    public DuplicateIdException() {
        super(Response.Status.BAD_REQUEST.getStatusCode(), "This ID already exists", "Something went wrong :( ", "");
    }
}
