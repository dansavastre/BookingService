package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping("/sayHiToRoom")
    public String sayHi() {
        return "Hello from the room!";
    }

    @RequestMapping("/getConnectionStatus")
    public String connectionStatus() {
        return "Rooms is connected!";
    }

    @RequestMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @RequestMapping("/getRoom/{id}")
    public Room getRoom(@PathVariable("id") int id) {
        return roomService.getRoom(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rooms")
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/rooms/{id}")
    public void updateRoom(@RequestBody Room room, @PathVariable("id") int id) {
        roomService.updateRoom(id, room);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rooms/{id}")
    public void deleteRoom(@PathVariable("id") int id) {
        roomService.deleteRoom(id);
    }
}
