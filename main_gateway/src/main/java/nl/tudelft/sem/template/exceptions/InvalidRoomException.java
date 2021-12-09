package nl.tudelft.sem.template.exceptions;

public class InvalidRoomException extends Exception {
    private static final long serialVersionUID = 1524835134673719001L;

    public InvalidRoomException(String errorMessage) {
        super(errorMessage);
    }
}
