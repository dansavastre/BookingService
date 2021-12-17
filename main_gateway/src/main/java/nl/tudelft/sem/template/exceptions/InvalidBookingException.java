package nl.tudelft.sem.template.exceptions;

public class InvalidBookingException extends Exception {
    private static final long serialVersionUID = 1524835134673719001L;

    public InvalidBookingException(String errorMessage) {
        super(errorMessage);
    }

}
