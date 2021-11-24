package nl.tudelft.sem.template.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BookingsController {

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the bookings microservice!";
    }

    @RequestMapping("/confirmRoomsConnection")
    public String checkIfRoomsConnected(){
        String uri = "http://localhost:8082/getConnectionStatus";
        RestTemplate template = new RestTemplate();
        String result = "Not connected :(";
        try {
            result = template.getForObject(uri, String.class);
        }catch(Exception e){
            result = "Not connected due to Error: " + e.toString();
        }
        return result;
    }
}
