package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Room;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RoomController {

    /** Returns all the rooms in the system.
     *
     * @return list of rooms.
     */
    @RequestMapping("/getRooms")
    public List getRooms() {
        String uri = "http://localhost:8082/rooms";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }

    /** Returns a specific room with respect to its id.
     *
     * @param id the id of the room we want.
     * @return the room we are searching for.
     */
    @RequestMapping("/getRoom/{id}")
    public Room getRoom(@PathVariable("id") int id) {
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(id));
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Room.class);
    }

    /** Adds a room to the system.
     *
     * @param room the room we want to add.
     * @return true if its successfully added, else false.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/postRoom")
    public boolean postRoom(@RequestBody Room room) {
        try {
            String uri = "http://localhost:8082/rooms";
            RestTemplate template = new RestTemplate();
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
    @RequestMapping(method = RequestMethod.PUT, value = "/putRoom/{id}")
    public boolean updateRoom(@RequestBody Room room, @PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
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
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteRoom/{id}")
    public boolean deleteRoom(@PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
