package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class RoomController {

    @Autowired
    private transient RestTemplate template;


    /** Returns all the rooms in the system.
     *
     * @return list of rooms.
     */
    @GetMapping("/getRooms")
    @ResponseBody
    public List getRooms() {
        String uri = "http://localhost:8082/rooms";
        return template.getForObject(uri, List.class);
    }

    /** Returns a specific room with respect to its id.
     *
     * @param id the id of the room we want.
     * @return the room we are searching for.
     */
    @GetMapping("/getRoom/{id}")
    @ResponseBody
    public Room getRoom(@PathVariable("id") int id) {
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(id));
        return template.getForObject(uri, Room.class);
    }

    /** Adds a room to the system.
     *
     * @param room the room we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postRoom")
    @ResponseBody
    public boolean postRoom(@RequestBody Room room) {
        try {
            String uri = "http://localhost:8082/rooms";
            template.postForObject(uri, room, void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Update a room.
     *
     * @param room the new room.
     * @param id the id of the room to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putRoom/{id}")
    @ResponseBody
    public boolean updateRoom(@RequestBody Room room, @PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            template.put(uri, room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Deletes a room from the system.
     *
     * @param id the id of the room to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteRoom/{id}")
    @ResponseBody
    public boolean deleteRoom(@PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            template.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
