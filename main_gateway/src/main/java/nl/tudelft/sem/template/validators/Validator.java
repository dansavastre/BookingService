package nl.tudelft.sem.template.validators;

import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public interface Validator {

    void setNext(Validator handler);

    boolean handle(Booking booking) throws InvalidBookingException, InvalidRoomException, BuildingNotOpenException;
}
