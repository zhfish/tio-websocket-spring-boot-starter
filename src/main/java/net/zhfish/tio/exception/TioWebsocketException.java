package net.zhfish.tio.exception;

public class TioWebsocketException extends RuntimeException {

    public TioWebsocketException() {
    }

    public TioWebsocketException(String message) {
        super(message);
    }

    public TioWebsocketException(String message, Throwable cause) {
        super(message, cause);
    }

    public TioWebsocketException(Throwable cause) {
        super(cause);
    }

    public TioWebsocketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
