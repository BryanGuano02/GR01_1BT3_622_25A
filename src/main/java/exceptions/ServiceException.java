package exceptions;

public class ServiceException extends Exception {
    public ServiceException(String message) {
        super(message);
    }
    // Constructor con mensaje y causa
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}