package nl.tudelft.sem.template.controllers;

import java.awt.print.Book;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.sem.template.exceptions.InvalidBookingException;
import nl.tudelft.sem.template.objects.Booking;
import nl.tudelft.sem.template.objects.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class RoomController {

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

}
