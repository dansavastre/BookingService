package nl.tudelft.sem.template.validators;

import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public abstract class BaseValidator implements Validator {

    private transient Validator next;

    public void setNext(Validator validator) {
        this.next = validator;
    }

    protected boolean checkNext(Booking booking) throws InvalidBookingException,
        InvalidRoomException, BuildingNotOpenException {
        if (next == null) {
            return true;
        }
        return next.handle(booking);
    }

    public Validator getNext() {
        return next;
    }


}
