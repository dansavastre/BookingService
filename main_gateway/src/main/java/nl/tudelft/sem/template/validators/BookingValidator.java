package nl.tudelft.sem.template.validators;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import nl.tudelft.sem.template.controllers.BookingController;
import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.controllers.RoomController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public class BookingValidator extends BaseValidator {

    private transient BuildingController buildingController = new BuildingController();
    private transient RoomController roomController = new RoomController();
    private transient BookingController bookingController = new BookingController();

    private boolean checkOtherBookings(Booking newBooking) {
        List<Booking> bookings = bookingController.getBookings();
        for(Booking booking : bookings) {
            if(booking.getDate().equals(newBooking.getDate())) {
                if((booking.getStartTime().compareTo(newBooking.getStartTime()) < 0 && booking.getEndTime().compareTo(newBooking.getEndTime()) < 0) ||
                    (booking.getStartTime().compareTo(newBooking.getStartTime()) >=0) && booking.getEndTime().compareTo(newBooking.getEndTime()) >= 0) {
                    // Bookings overlap
                    return false;
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
        } else if (buildingController.getBuilding(booking.getBuilding()) == null) {
            throw new InvalidBookingException("Building does not exist");
        } else if (roomController.getRoom(booking.getRoom()) == null) {
            throw new InvalidBookingException("Room does not exist");
        } else if(booking.getStartTime().compareTo(booking.getEndTime()) >= 0) {
            throw new InvalidBookingException("Start time is after end time");
        } else if(!checkOtherBookings(booking)) {
            throw new InvalidBookingException("Booking overlaps with another booking");
        }

        return super.checkNext(booking);
    }
}
