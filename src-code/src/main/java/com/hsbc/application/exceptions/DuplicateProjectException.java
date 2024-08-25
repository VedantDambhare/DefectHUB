package com.hsbc.application.exceptions;

public class DuplicateProjectException extends BugTrackingException {
    public DuplicateProjectException(String message) {
        super(message);
    }

    public DuplicateProjectException() {
    }

    public DuplicateProjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateProjectException(Throwable cause) {
        super(cause);
    }

    public DuplicateProjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
