package nl.tudelft.sem.main.validators;

import nl.tudelft.sem.main.controllers.BuildingController;
import nl.tudelft.sem.main.controllers.SecondBuildingController;
import nl.tudelft.sem.main.exceptions.BuildingNotOpenException;
import nl.tudelft.sem.main.exceptions.InvalidBookingException;
import nl.tudelft.sem.main.exceptions.InvalidRoomException;
import nl.tudelft.sem.main.objects.Booking;
import nl.tudelft.sem.main.objects.Building;

public class BuildingValidator extends BaseValidator {

    private transient String token;

    private transient BuildingController buildingController;

    private transient SecondBuildingController secondBuildingController;

    public BuildingValidator(BuildingController buildingController,
                             SecondBuildingController secondBuildingController) {
        this.buildingController = buildingController;
        this.secondBuildingController = secondBuildingController;
    }


    @Override
    public boolean handle(Booking booking) throws BuildingNotOpenException,
        InvalidBookingException, InvalidRoomException {
        Building building = this.secondBuildingController.getBuilding(booking.getBuilding(), token);
        if (booking.getStartTime().compareTo(building.getOpeningTime()) < 0
            || booking.getEndTime().compareTo(building.getClosingTime()) > 0) {
            throw new BuildingNotOpenException("Building is not open during this interval");
        }
        return super.checkNext(booking);
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
