package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import nl.tudelft.sem.template.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public Class RoomController {

    @Autowired
    private transient RoomService roomService;

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
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/getRoom/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable("id") int id) {
        return roomService.getRoom(id);
    }

    @PostMapping("/rooms")
    @ResponseBody
    public void addRoom(@RequestBody Room room) {
        roomService.addRoom(room);
    }

    @PutMapping("/rooms/{id}")
    @ResponseBody
    public void updateRoom(@RequestBody Room room, @PathVariable("id") int id) {
        roomService.updateRoom(id, room);
    }

    @DeleteMapping("/rooms/{id}")
    @ResponseBody
    public void deleteRoom(@PathVariable("id") int id) {
        roomService.deleteRoom(id);
    }
}
