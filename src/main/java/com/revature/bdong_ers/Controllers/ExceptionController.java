package com.revature.bdong_ers.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ExceptionController {
    
    private static final Logger LOGGER = LogManager.getLogger();
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    public void handleConflict() {
        LOGGER.error("Account already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public void handleBadRequest() {
        LOGGER.error("Bad request");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public void handleUnauthorized() {
        // Multiple unauthorized requests with the same username, IP, or whatever information would be relevant
        // could trigger different theoretical code to lock out the user from logging in for a specified timeout duration (5 min for example)
        LOGGER.error("Unauthorized");
    }
}
