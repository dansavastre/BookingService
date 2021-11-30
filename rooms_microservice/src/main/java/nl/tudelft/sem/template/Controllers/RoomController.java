package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.Room;
import nl.tudelft.sem.template.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @RequestMapping("/sayHiToRoom")
    public String sayHi(){
        return "Hello from the room!";
    }

    @RequestMapping("/getConnectionStatus")
    public String connectionStatus(){
        return "Rooms is connected!";
    }

    @RequestMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @RequestMapping("/getRoom/{id}")
    public Room getRoom(@PathVariable("id") int id){
        return roomService.getRoom(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/rooms")
    public void addRoom(@RequestBody Room room){
        roomService.addRoom(room);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/rooms/{id}")
    public void updateRoom(@RequestBody Room room, @PathVariable("id") int id){
        roomService.updateRoom(id, room);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/rooms/{id}")
    public void deleteRoom(@PathVariable("id") int id){
        roomService.deleteRoom(id);
    }
}
