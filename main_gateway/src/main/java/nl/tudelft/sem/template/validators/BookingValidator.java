package nl.tudelft.sem.template.validators;

import java.time.LocalDate;
import java.time.LocalTime;
import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.controllers.RoomController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;

public class BookingValidator extends BaseValidator {

    private transient BuildingController buildingController = new BuildingController();
    private transient RoomController roomController = new RoomController();

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
        }

        return super.checkNext(booking);
    }
}
