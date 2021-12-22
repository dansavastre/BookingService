package nl.tudelft.sem.template.validators;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import nl.tudelft.sem.template.controllers.BookingController;
import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.controllers.RoomController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public class BookingValidator extends BaseValidator {

    private transient BuildingController buildingController;
    private transient RoomController roomController;
    private transient BookingController bookingController;

    private transient String token;
    private transient int period = 14;

    /** Constructor for BookingValidator.
     *
     * @param buildingController    building Controller
     * @param roomController        room Controller
     * @param bookingController     booking Controller
     */
    public BookingValidator(BuildingController buildingController,
                            RoomController roomController,
                            BookingController bookingController) {
        this.buildingController = buildingController;
        this.roomController = roomController;
        this.bookingController = bookingController;
    }

    /**
     * Method for checking if the owner of the new booking has other bookings at the same time.
     *
     * @param newBooking the new booking
     * @return true if there are no overlapping bookings, false otherwise
     */
    private boolean checkOtherBookings(Booking newBooking) {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        List<Booking> bookings = om.convertValue(bookingController.getAllBookings(token),
                new TypeReference<List<Booking>>() {});
        for (Booking booking : bookings) {
            //Check if booking owner is the same
            if (booking.getBookingOwner().equals(newBooking.getBookingOwner())) {
                //Check if date is the same
                if (booking.getDate().equals(newBooking.getDate())) {
                    //Check if times overlap
                    if ((newBooking.getStartTime().compareTo(booking.getStartTime()) >= 0
                            && newBooking.getStartTime().compareTo(booking.getEndTime()) < 0)
                            || (newBooking.getEndTime().compareTo(booking.getStartTime()) > 0
                            && newBooking.getEndTime().compareTo(booking.getEndTime()) <= 0)) {
                        // Bookings overlap
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean handle(Booking booking) throws InvalidBookingException,
        InvalidRoomException, BuildingNotOpenException {

        if (booking.getDate().compareTo(LocalDate.now()) < 0) {
            throw new InvalidBookingException("Date of booking is in the past");
        } else if (booking.getDate().compareTo(LocalDate.now()) == 0
            && booking.getStartTime().compareTo(LocalTime.now()) <= 0) {
            throw  new InvalidBookingException("Booking start time is before current time");
        } else if (buildingController.getBuilding(booking.getBuilding(), token) == null) {
            throw new InvalidBookingException("Building does not exist");
        } else if (roomController.getRoom(booking.getRoom(), token) == null) {
            throw new InvalidBookingException("Room does not exist");
        } else if (booking.getStartTime().compareTo(booking.getEndTime()) >= 0) {
            throw new InvalidBookingException("Start time is after end time");
        } else if (!checkOtherBookings(booking)) {
            throw new InvalidBookingException("Booking overlaps with another booking");
        } else if (ChronoUnit.DAYS.between(LocalDate.now(), booking.getDate()) > period) {
            throw new InvalidBookingException("Cannot make a booking more than 2 weeks in advance");
        }

        return super.checkNext(booking);
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
