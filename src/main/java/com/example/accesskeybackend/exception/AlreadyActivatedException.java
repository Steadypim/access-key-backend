package com.example.accesskeybackend.exception;

public class AlreadyActivatedException extends BaseException {
    public AlreadyActivatedException() {
    }

    public AlreadyActivatedException(String message) {
        super(message);
    }

    public AlreadyActivatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyActivatedException(Throwable cause) {
        super(cause);
    }

    public AlreadyActivatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
