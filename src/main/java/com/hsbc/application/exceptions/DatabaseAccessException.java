package com.hsbc.application.exceptions;

public class DatabaseAccessException extends BugTrackingException {
    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException() {
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseAccessException(Throwable cause) {
        super(cause);
    }

    public DatabaseAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
