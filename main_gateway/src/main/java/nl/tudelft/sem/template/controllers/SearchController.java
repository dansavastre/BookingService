package nl.tudelft.sem.template.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;


public class SearchController {

    @Autowired
    private transient BookingController bookingController;

    @Autowired
    private transient MainRoomController roomController;

    /**
     * This method allows users to search with a date, start time and end time and returns the
     * corresponding available rooms.
     *
     * @param date      String of date in form "2021-12-31"
     * @param startTime String of start time in form "08:30:00"
     * @param endTime   String of end time in form "08:30:00" (must be after start time)
     * @param token     the token of the user for authentication
     * @return a list of available rooms
     */
    @GetMapping("/rooms/search/availability")
    @ResponseBody
    public List<Room> availableRooms(@RequestParam String date, @RequestParam String startTime,
                                     @RequestParam String endTime,
                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        LocalTime realStartTime = LocalTime.parse(startTime);
        LocalTime realEndTime = LocalTime.parse(endTime);
        if (realEndTime.isBefore(realStartTime)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "End time is before start time");
        }
        LocalDate realDate = LocalDate.parse(date);
        if (realDate.isBefore(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Date is in the past");
        }
        if (realDate.isEqual(LocalDate.now()) && realStartTime.isBefore(LocalTime.now())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Start time is in the past");
        }
        List<Room> allRooms = objectMapper.convertValue(roomController.getRooms(token),
                new TypeReference<List<Room>>() {
                });
        List<Booking> allBookings = objectMapper.convertValue(bookingController
                        .getFutureBookings(token),
                new TypeReference<List<Booking>>() {
                });
        allRooms.removeIf(r -> !r.getAvailable().equals("yes"));
        allRooms.removeIf(r -> realStartTime.compareTo(r.getBuilding().getOpeningTime()) < 0
                || realEndTime.compareTo(r.getBuilding().getClosingTime()) > 0);

        for (Booking b : allBookings) {
            List<Room> placeholder = new ArrayList<>(allRooms);
            for (Room r : placeholder) {
                if (b.getRoom() == r.getRoomNumber()
                        && b.getBuilding() == r.getBuilding().getId()) {
                    if (b.getDate() == realDate && (realStartTime.compareTo(b.getStartTime()) >= 0
                            && realStartTime.compareTo(b.getEndTime()) < 0)
                            || (realEndTime.compareTo(b.getStartTime()) > 0
                            && realEndTime.compareTo(b.getEndTime()) <= 0)) {
                        allRooms.remove(r);
                    }
                }
            }
        }
        return allRooms;
    }

    /**
     * Search method so the user can give optional search parameters on which they want to filter
     * rooms by.
     *
     * @param date           of date in form "2021-12-31"
     * @param startTime      String of start time in form "08:30:00"
     * @param endTime        String of end time in form "08:30:00" (must be after start time)
     * @param roomNumber     String of room number which the user wants
     * @param buildingNumber String of building number which the user wants
     * @param roomName       String of room name, the corresponding room name the user wants
     * @param capacity       String of capacity, the minimum capacity needed for the user
     * @param equipment      String which is a list of equipment,
     *                       in the form of "Whiteboard, Computer,..."
     * @param token          the token of the user for authentication
     * @return the list of available rooms which match the search characteristics given by the user
     */
    @GetMapping("/rooms/search")
    @ResponseBody
    public List<Room> searchRooms(@RequestParam(required = false) String date,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                  @RequestParam(required = false) String roomNumber,
                                  @RequestParam(required = false) String buildingNumber,
                                  @RequestParam(required = false) String roomName,
                                  @RequestParam(required = false) String capacity,
                                  @RequestParam(required = false) String[] equipment,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        List<Room> allRooms;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        if (date != null && startTime != null && endTime != null) {
            allRooms = objectMapper.convertValue(availableRooms(date, startTime, endTime, token),
                    new TypeReference<List<Room>>() {
                    });
        } else {
            allRooms = objectMapper.convertValue(roomController.getRooms(token),
                    new TypeReference<List<Room>>() {
                });
            allRooms.removeIf(r -> !r.getAvailable().equals("yes"));

        }
        if (roomNumber != null) {
            allRooms.removeIf(r -> r.getRoomNumber() != Integer.parseInt(roomNumber));
        }
        if (buildingNumber != null) {
            allRooms.removeIf(r -> r.getBuilding().getId() != Integer.parseInt(buildingNumber));
        }
        if (roomName != null) {
            allRooms.removeIf(r -> !r.getName().equals(roomName));
        }
        if (capacity != null) {
            allRooms.removeIf(r -> r.getCapacity() < Integer.parseInt(capacity));
        }
        if (equipment != null) {
            List<Room> placeholder = new ArrayList<>(allRooms);
            for (Room r : placeholder) {
                for (String e : equipment) {
                    Map<String, String> equipmentList = r.getEquipment();
                    if (!equipmentList.containsKey(e) || equipmentList.get(e).equals("False")) {
                        allRooms.remove(r);
                        break;
                    }
                }
            }
        }
        return allRooms;
    }
}
