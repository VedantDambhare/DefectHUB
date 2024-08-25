package com.hsbc.application.exceptions;

public class ProjectNotFoundException extends BugTrackingException {
    public ProjectNotFoundException(String message) {
        super(message);
    }

    public ProjectNotFoundException() {
    }

    public ProjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ProjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
