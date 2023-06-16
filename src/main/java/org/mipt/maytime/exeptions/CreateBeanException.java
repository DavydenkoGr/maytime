package org.mipt.maytime.exeptions;

/**
 * CreateBeanException throws when there are a problems with bean instantiation
 *
 * @author Davydenko Grigorii
 */
public class CreateBeanException extends RuntimeException {
    public CreateBeanException() {
        super();
    }

    public CreateBeanException(String message) {
        super(message);
    }

    public CreateBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateBeanException(Throwable cause) {
        super(cause);
    }
}
