package nl.tudelft.sem.main.validators;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.main.controllers.BookingController;
import nl.tudelft.sem.main.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.main.exceptions.InvalidBookingException;
import nl.tudelft.sem.main.exceptions.InvalidRoomException;
import nl.tudelft.sem.main.objects.Booking;

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
    public String getToken() {
        return this.token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }

}
