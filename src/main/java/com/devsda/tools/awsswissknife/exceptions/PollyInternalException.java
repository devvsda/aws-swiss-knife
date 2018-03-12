package com.devsda.tools.awsswissknife.exceptions;

public class PollyInternalException extends RuntimeException {

    public PollyInternalException(String message) {
        super(message);
    }

    public PollyInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PollyInternalException(Throwable cause) {
        super(cause);
    }
}
