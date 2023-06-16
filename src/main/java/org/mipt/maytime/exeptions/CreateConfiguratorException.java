package org.mipt.maytime.exeptions;

/**
 * CreateConfiguratorException throws when there are a problems with configurators creation
 *
 * @author Davydenko Grigorii
 */
public class CreateConfiguratorException extends RuntimeException {
    public CreateConfiguratorException() {
        super();
    }

    public CreateConfiguratorException(String message) {
        super(message);
    }

    public CreateConfiguratorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CreateConfiguratorException(Throwable cause) {
        super(cause);
    }
}
