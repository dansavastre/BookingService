package nl.tudelft.sem.template.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the rooms microservice!";
    }

    @RequestMapping("/getConnectionStatus")
    public String connectionStatus(){
        return "Rooms is connected!";
    }
}