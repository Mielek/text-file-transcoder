package com.github.mielek;

public class TranscodeException extends Exception{
    public TranscodeException() {
    }

    public TranscodeException(String message) {
        super(message);
    }

    public TranscodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranscodeException(Throwable cause) {
        super(cause);
    }

    public TranscodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
