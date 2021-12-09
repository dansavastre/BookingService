package nl.tudelft.sem.template.validators;

import nl.tudelft.sem.template.controllers.BookingController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Room;

import java.util.List;

public class RoomValidator extends BaseValidator {

    BookingController bookingController;

    public boolean bookingsOverlap(Booking booking, Booking other) {
        if (booking.getRoom() == other.getRoom() &&
            booking.getDate().equals(other.getDate())) {
            if ((booking.getEndTime().compareTo(other.getStartTime()) >= 0
                && booking.getEndTime().compareTo(other.getEndTime()) > 0)
                || (booking.getStartTime().compareTo(other.getStartTime()) >= 0
                && booking.getStartTime().compareTo(other.getEndTime()) < 0)) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean handle(Booking newBooking) throws InvalidRoomException,
        InvalidBookingException, BuildingNotOpenException {
        List<Booking> bookings = bookingController.getBookings();
        for (Booking booking : bookings) {
            if (bookingsOverlap(newBooking, booking)) {
                throw new InvalidRoomException("The room is not available during this interval");
            }
        }
        return super.checkNext(newBooking);
    }

}
