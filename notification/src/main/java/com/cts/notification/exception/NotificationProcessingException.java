package com.cts.notification.exception;

public class NotificationProcessingException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotificationProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotificationProcessingException(String message) {
        super(message);
    }
}
