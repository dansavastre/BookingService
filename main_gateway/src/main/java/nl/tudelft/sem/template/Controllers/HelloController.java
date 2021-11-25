package nl.tudelft.sem.template.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @RequestMapping("/helloRooms")
    public String helloRooms(){
        String uri = "http://localhost:8082/sayHi";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }

    @RequestMapping("/helloBookings")
    public String helloBookings(){
        String uri = "http://localhost:8083/sayHi";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }

    @RequestMapping("/checkBookingsConnectionToRoom")
    public String checkConnectionBookingsRooms(){
        String uri = "http://localhost:8083/confirmRoomsConnection";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }
}
