package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the user microservice!";
    }

    @RequestMapping("/addUser")
    public String addUser(){
        String uri = "http://localhost:8080/addUser";
        RestTemplate template = new RestTemplate();
        User result = template.getForObject(uri, User.class);
        userService.save(result);
        return result.toString();
    }

    @RequestMapping("/getUsers")
    public Collection<User> getUsers() {
        return userService.findAll();
    }
}
