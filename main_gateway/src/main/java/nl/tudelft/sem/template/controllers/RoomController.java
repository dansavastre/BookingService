package nl.tudelft.sem.template.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RoomController {

    @Autowired
    private transient RestTemplate restTemplate;

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
    public Room getRoom(@PathVariable("id") int id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/getRoom/".concat(String.valueOf(id));
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
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
    public boolean updateRoom(@RequestBody Room room, @PathVariable("id") int id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
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
    public boolean deleteRoom(@PathVariable("id") int id,
                              @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/rooms/".concat(String.valueOf(id));
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

}
