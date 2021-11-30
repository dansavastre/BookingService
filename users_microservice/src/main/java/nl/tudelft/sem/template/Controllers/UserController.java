package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the user microservice!";
    }

    @RequestMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") String id){
        return userService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }
}
