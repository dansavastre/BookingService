package nl.tudelft.sem.template.exceptions;

public class InvalidBookingException extends Exception {

    public InvalidBookingException(String errorMessage) {
        super(errorMessage);
    }

}
