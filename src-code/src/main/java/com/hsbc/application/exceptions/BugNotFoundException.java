package com.hsbc.application.exceptions;

public class BugNotFoundException extends BugTrackingException {
    public BugNotFoundException(String message) {
        super(message);
    }

    public BugNotFoundException() {
    }

    public BugNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BugNotFoundException(Throwable cause) {
        super(cause);
    }

    public BugNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
