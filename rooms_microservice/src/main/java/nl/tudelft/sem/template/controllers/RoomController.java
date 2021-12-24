package nl.tudelft.sem.template.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RoomController {

    @Autowired
    private transient RoomService roomService;
    @Autowired
    private transient Authorization auth;

    private final transient String authorization = "Authorization";

    @GetMapping("/sayHiToRoom")
    @ResponseBody
    public String sayHi() {
        return "Hello from the room!";
    }

    @GetMapping("/getConnectionStatus")
    @ResponseBody
    public String connectionStatus() {
        return "Rooms is connected!";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> getAllRooms(@RequestHeader(authorization) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return roomService.getAllRooms();
    }

    @GetMapping("/getRoom/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable("id") String id,
                        @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.EMPLOYEE, token);
        return roomService.getRoom(id);
    }

    @PostMapping("/rooms")
    @ResponseBody
    public void addRoom(@RequestBody Room room,
                        @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        roomService.addRoom(room);
    }

    @PutMapping("/rooms/{id}")
    @ResponseBody
    public void updateRoom(@RequestBody Room room,
                           @PathVariable("id") String id,
                           @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        roomService.updateRoom(id, room);
    }

    @DeleteMapping("/rooms/{id}")
    @ResponseBody
    public void deleteRoom(@PathVariable("id") String id,
                           @RequestHeader(authorization) String token) {
        auth.authorize(Authorization.ADMIN, token);
        roomService.deleteRoom(id);
    }

}
