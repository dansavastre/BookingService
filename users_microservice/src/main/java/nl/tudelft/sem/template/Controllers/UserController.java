package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Services.IUserService;
import nl.tudelft.sem.template.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {


    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the user microservice!";
    }

    @RequestMapping("/getUsers")
    public List<User> getUser(){
        IUserService userService = new UserService();
        return userService.findAll();
    }
}
