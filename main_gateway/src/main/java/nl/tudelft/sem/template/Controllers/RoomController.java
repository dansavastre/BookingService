package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.Room;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class RoomController {

    @RequestMapping("/getRooms")
    public List<Room> getRooms(){
        String uri = "http://localhost:8082/rooms";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }


    @RequestMapping("/getRoom/{id}")
    public Room getRoom(@PathVariable("id") int id){
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(id));
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Room.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postRoom")
    public boolean postRoom(@RequestBody Room room){
        try {
            String uri = "http://localhost:8082/rooms";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, room, void.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putRoom/{id}")
    public boolean updateRoom(@RequestBody Room room, @PathVariable("id") int id){
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.put(uri, room);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteRoom/{id}")
    public boolean deleteRoom(@PathVariable("id") int id){
        try {
            String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
