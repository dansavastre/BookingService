package nl.tudelft.sem.main.validators;

import nl.tudelft.sem.main.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.main.exceptions.InvalidBookingException;
import nl.tudelft.sem.main.exceptions.InvalidRoomException;
import nl.tudelft.sem.main.objects.Booking;

public interface Validator {

    void setNext(Validator handler);

    boolean handle(Booking booking) throws InvalidBookingException,
            InvalidRoomException, BuildingNotOpenException;

    void setToken(String token);
}
