package nl.tudelft.sem.template.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloController {

    /** Receives a message from the user microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloUser")
    @ResponseBody
    public String helloUser() {
        String uri = "http://localhost:8081/sayHi";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloRooms")
    @ResponseBody
    public String helloRoom() {
        String uri = "http://localhost:8082/sayHiToRoom";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloBuildings")
    @ResponseBody
    public String helloBuilding() {
        String uri = "http://localhost:8082/sayHiToBuilding";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloBookings")
    @ResponseBody
    public String helloBookings() {
        String uri = "http://localhost:8083/sayHi";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @GetMapping("/checkBookingsConnectionToRoom")
    @ResponseBody
    public String checkConnectionBookingsRooms() {
        String uri = "http://localhost:8083/confirmRoomsConnection";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }
}
