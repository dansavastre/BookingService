package nl.tudelft.sem.template.validators;

import nl.tudelft.sem.template.controllers.BuildingController;
import nl.tudelft.sem.template.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.exceptions.InvalidRoomException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Building;
import org.springframework.beans.factory.annotation.Autowired;

public class BuildingValidator extends BaseValidator {

    @Autowired
    private transient BuildingController buildingController;

    @Override
    public boolean handle(Booking booking) throws BuildingNotOpenException,
        InvalidBookingException, InvalidRoomException {
        Building building = buildingController.getBuilding(booking.getBuilding());
        if (booking.getStartTime().compareTo(building.getOpeningTime()) < 0
            || booking.getEndTime().compareTo(building.getClosingTime()) > 0) {
            throw new BuildingNotOpenException("Building is not open during this interval");
        }
        return super.checkNext(booking);
    }
}
