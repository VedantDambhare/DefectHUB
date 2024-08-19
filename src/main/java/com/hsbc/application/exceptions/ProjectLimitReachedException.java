package com.hsbc.application.exceptions;

public class ProjectLimitReachedException extends BugTrackingException {
    public ProjectLimitReachedException(String message) {
        super(message);
    }

    public ProjectLimitReachedException() {
    }

    public ProjectLimitReachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectLimitReachedException(Throwable cause) {
        super(cause);
    }

    public ProjectLimitReachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
