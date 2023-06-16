package org.mipt.maytime.exeptions;

/**
 * RepeatingBeanException throws when there are two methods annotated with @Bean
 * which return the same class
 *
 * @author Davydenko Grigorii
 */
public class RepeatingBeanException extends RuntimeException {
    public RepeatingBeanException() {
        super();
    }

    public RepeatingBeanException(String message) {
        super(message);
    }

    public RepeatingBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatingBeanException(Throwable cause) {
        super(cause);
    }
}
