package org.mipt.maytime.exeptions;

/**
 * LoadFileException throws when there are a problems with loading user files
 *
 * @author Davydenko Grigorii
 */
public class LoadFileException extends RuntimeException {
    public LoadFileException() {
        super();
    }

    public LoadFileException(String message) {
        super(message);
    }

    public LoadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadFileException(Throwable cause) {
        super(cause);
    }
}
