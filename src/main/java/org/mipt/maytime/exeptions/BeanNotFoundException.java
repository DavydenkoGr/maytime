package org.mipt.maytime.exeptions;

/**
 * BeanNotFoundException throws when user tries to get absence bean
 *
 * @author Davydenko Grigorii
 */
public class BeanNotFoundException extends RuntimeException {
    public BeanNotFoundException() {
        super();
    }

    public BeanNotFoundException(String message) {
        super(message);
    }

    public BeanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanNotFoundException(Throwable cause) {
        super(cause);
    }
}
