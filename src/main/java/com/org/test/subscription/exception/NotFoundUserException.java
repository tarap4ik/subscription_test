package com.org.test.subscription.exception;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String user) {
        super(MessageFormat.format("Пользватель с идентификатором {0} не был найден", user));
        log.error(getMessage());
    }
}
