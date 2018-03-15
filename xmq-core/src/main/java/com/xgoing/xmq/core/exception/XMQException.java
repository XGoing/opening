package com.xgoing.xmq.core.exception;

public class XMQException extends RuntimeException {
    public XMQException() {
        super();
    }

    public XMQException(String message) {
        super(message);
    }

    public XMQException(String message, Throwable cause) {
        super(message, cause);
    }

    public XMQException(Throwable cause) {
        super(cause);
    }

    protected XMQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
