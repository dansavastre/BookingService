package nl.tudelft.sem.template.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class HelloController {

    @Autowired
    private transient RestTemplate restTemplate;


    /** Receives a message from the user microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloUser")
    @ResponseBody
    public String helloUser() {
        String uri = "http://localhost:8081/sayHi";

        return restTemplate.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloRooms")
    @ResponseBody
    public String helloRoom() {
        String uri = "http://localhost:8082/sayHiToRoom";
        return restTemplate.getForObject(uri, String.class);
    }

    /** Receives a message from the room microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloBuildings")
    @ResponseBody
    public String helloBuilding() {
        String uri = "http://localhost:8082/sayHiToBuilding";
        return restTemplate.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @GetMapping("/helloBookings")
    @ResponseBody
    public String helloBookings() {
        String uri = "http://localhost:8083/sayHi";
        return restTemplate.getForObject(uri, String.class);
    }

    /** Receives a message from the booking microservice.
     *
     * @return a string.
     */
    @GetMapping("/checkBookingsConnectionToRoom")
    @ResponseBody
    public String checkConnectionBookingsRooms() {
        String uri = "http://localhost:8083/confirmRoomsConnection";
        return restTemplate.getForObject(uri, String.class);
    }
}
