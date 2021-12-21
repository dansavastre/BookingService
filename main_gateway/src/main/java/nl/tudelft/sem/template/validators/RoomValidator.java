package nl.tudelft.sem.template.validators;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import nl.tudelft.sem.template.controllers.BookingController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public class RoomValidator extends BaseValidator {

    private transient String token;
    private transient BookingController bookingController;

    public RoomValidator(BookingController bookingController) {
        this.bookingController = bookingController;
    }

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
        List<Booking> bookings = om.convertValue(bookingController.getAllBookings(token),
                new TypeReference<List<Booking>>() {});
        for (Booking booking : bookings) {
            if (bookingsOverlap(newBooking, booking)) {
                throw new InvalidRoomException("The room is not available during this interval");
            }
        }
        return super.checkNext(newBooking);
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

}
