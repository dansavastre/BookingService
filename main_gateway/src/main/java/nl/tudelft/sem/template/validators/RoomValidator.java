package nl.tudelft.sem.template.validators;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.sem.template.controllers.BookingController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomValidator extends BaseValidator {

    @Autowired
    private transient BookingController bookingController;

    /**
     * Method for checking if two bookings overlap.
     *
     * @param booking the booking we check for validity
     * @param other a booking from the database
     * @return true if the bookings overlap, false otherwise
     */
    public boolean bookingsOverlap(Booking booking, Booking other) {
        if (booking.getRoom() == other.getRoom()
            && booking.getDate().equals(other.getDate())) {
            if ((booking.getEndTime().compareTo(other.getStartTime()) >= 0
                && booking.getEndTime().compareTo(other.getEndTime()) > 0)
                || (booking.getStartTime().compareTo(other.getStartTime()) >= 0
                && booking.getStartTime().compareTo(other.getEndTime()) < 0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handle(Booking newBooking) throws InvalidRoomException,
        InvalidBookingException, BuildingNotOpenException {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        List<Booking> bookings = om.convertValue(bookingController.getAllBookings(),
                new TypeReference<List<Booking>>() {});
        for (Booking booking : bookings) {
            if (bookingsOverlap(newBooking, booking)) {
                throw new InvalidRoomException("The room is not available during this interval");
            }
        }
        return super.checkNext(newBooking);
    }

}
