package nl.tudelft.sem.template.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class    UserController {
    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the user microservice!";
    }
}
