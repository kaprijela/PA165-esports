package cz.muni.fi.pa165.esports.exceptions;

/**
 * Exception converted by MyExceptionHandler to NOT_FOUND HTTP status.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
