package nl.tudelft.sem.main.validators;

import java.util.Optional;
import nl.tudelft.sem.main.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.main.exceptions.InvalidBookingException;
import nl.tudelft.sem.main.exceptions.InvalidRoomException;
import nl.tudelft.sem.main.objects.Booking;

public interface Validator {

    void setNext(Validator handler);

    boolean handle(Booking booking) throws InvalidBookingException,
            InvalidRoomException, BuildingNotOpenException;

    /**
     * Default implementation of bookingsOverlap
     * to avoid having to implement the method multiple times.
     *
     * @param booking The first of the two bookings to be checked
     * @param other   The second of the two bookings to be checked
     * @return Boolean indicating if they overlap or not
     */
    default boolean bookingsOverlap(Booking booking, Booking other) {
        if (booking.getRoom() == other.getRoom()
                && booking.getBuilding() == other.getBuilding()
                && booking.getDate().equals(other.getDate())
                && !other.getStatus().startsWith("cancelled")
                && !other.getId().equals(Optional.ofNullable(booking.getId()).orElse(0L))) {
            return (other.getStartTime().compareTo(booking.getStartTime()) >= 0
                    && other.getStartTime().compareTo(booking.getEndTime()) < 0)
                    || (other.getEndTime().compareTo(booking.getStartTime()) > 0
                    && other.getEndTime().compareTo(booking.getEndTime()) <= 0);
        }
        return false;
    }

    String getToken();

    void setToken(String token);
}
