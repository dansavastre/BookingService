package nl.tudelft.sem.template.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingsController {

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the bookings microservice!";
    }
}
