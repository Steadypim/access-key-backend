package com.example.accesskeybackend.exception;

public class AccessKeyProviderException extends BaseException {
    public AccessKeyProviderException() {
    }

    public AccessKeyProviderException(String message) {
        super(message);
    }

    public AccessKeyProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessKeyProviderException(Throwable cause) {
        super(cause);
    }

    public AccessKeyProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
