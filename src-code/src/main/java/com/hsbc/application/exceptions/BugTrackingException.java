package com.hsbc.application.exceptions;

public class BugTrackingException extends Exception {
    public BugTrackingException(String message) {
        super(message);
    }

    public BugTrackingException() {
    }

    public BugTrackingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BugTrackingException(Throwable cause) {
        super(cause);
    }

    public BugTrackingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
