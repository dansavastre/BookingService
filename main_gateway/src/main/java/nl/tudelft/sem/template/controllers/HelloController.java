package nl.tudelft.sem.template.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    /** Receives a message from the user microservice.
     *
     * @return a string.
     */
    @RequestMapping("/helloUser")
    public String helloUser() {
        String uri = "http://localhost:8081/sayHi";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @RequestMapping("/helloRooms")
    public String helloRoom() {
        String uri = "http://localhost:8082/sayHiToRoom";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @RequestMapping("/helloBuildings")
    public String helloBuilding() {
        String uri = "http://localhost:8082/sayHiToBuilding";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @RequestMapping("/helloBookings")
    public String helloBookings() {
        String uri = "http://localhost:8083/sayHi";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @RequestMapping("/checkBookingsConnectionToRoom")
    public String checkConnectionBookingsRooms() {
        String uri = "http://localhost:8083/confirmRoomsConnection";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, String.class);
    }
}
