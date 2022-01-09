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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class MainRoomController {

    @Autowired
    private transient RestTemplate restTemplate;

    @Autowired
    private transient BookingController bookingController;

    /**
     * Endpoint which connects to the room microservice and retrieves all rooms in the db.
     *
     * @param token Authorizes user
     * @return list of rooms
     */
    @GetMapping("/getRooms")
    @ResponseBody
    public List getRooms(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/rooms";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<List> res = restTemplate
                    .exchange(uri, HttpMethod.GET, entity, List.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Returns a specific room with respect to its id.
     *
     * @param id    the id of the room we want.
     * @param token the token of the user
     * @return the room we are searching for.
     */
    @GetMapping("/getRoom/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable("id") String id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/getRoom/".concat(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<Room> res = restTemplate
                    .exchange(uri, HttpMethod.GET, entity, Room.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Adds a room to the system.
     *
     * @param room  the room we want to add.
     * @param token the token of the user
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postRoom")
    @ResponseBody
    public boolean postRoom(@RequestBody Room room,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        String uri = "http://localhost:8082/rooms";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Room> entity = new HttpEntity<>(room, headers);
        try {
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * Update a room.
     *
     * @param room  the new room.
     * @param id    the id of the room to update.
     * @param token the token of the user
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putRoom/{id}")
    @ResponseBody
    public boolean updateRoom(@RequestBody Room room, @PathVariable("id") String id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        String uri = "http://localhost:8082/rooms/".concat(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Room> entity = new HttpEntity<>(room, headers);

        try {
            restTemplate.exchange(uri, HttpMethod.PUT, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Deletes a room from the system.
     *
     * @param id    the id of the room to delete.
     * @param token the token of the user
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteRoom/{id}")
    @ResponseBody
    public boolean deleteRoom(@PathVariable("id") String id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/rooms/".concat(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            restTemplate.exchange(uri, HttpMethod.DELETE, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

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
        List<Room> allRooms = objectMapper.convertValue(getRooms(token),
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
            allRooms = objectMapper.convertValue(getRooms(token), new TypeReference<List<Room>>() {
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
