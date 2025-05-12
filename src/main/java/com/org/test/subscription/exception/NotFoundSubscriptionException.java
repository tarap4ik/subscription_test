package com.org.test.subscription.exception;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
public class NotFoundSubscriptionException extends RuntimeException {
    public NotFoundSubscriptionException(String subscription) {
        super(MessageFormat.format("Подписка с идентификатором {0} не была найдена", subscription));
        log.error(getMessage());
    }
}
