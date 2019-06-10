package app.logic.loader.exceptions;

public class LXMLParsingException extends Exception {

    public LXMLParsingException() {
    }

    public LXMLParsingException(String message) {
        super(message);
    }

    public LXMLParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LXMLParsingException(Throwable cause) {
        super(cause);
    }

    public LXMLParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
