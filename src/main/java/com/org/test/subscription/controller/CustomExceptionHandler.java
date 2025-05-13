package com.org.test.subscription.controller;

import com.org.test.subscription.exception.NotFoundSubscriptionException;
import com.org.test.subscription.exception.NotFoundUserException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Hidden
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundUserException.class, NotFoundSubscriptionException.class})
    public void handleException() {
    }

}
