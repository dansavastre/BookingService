package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import nl.tudelft.sem.template.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class UserController {


    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/sayHi")
    public String sayHi(){
        return "Hello from the user microservice!";
    }

    @RequestMapping("/addUser")
    public String addUser(String id, String password, String fName, String lName, String userType){
        String uri = "http://localhost:8080/addUser?id=" + id +
                      "?password=" + password +
                      "?fName=" + fName +
                      "?lName=" + lName +
                      "?userType=" + userType;
        RestTemplate template = new RestTemplate();
        User result = template.getForObject(uri, User.class);
        userService.save(result);
        return "User added";
    }

    @RequestMapping("/getUsers")
    public String getUsers() {
        return (new User("id", "password", "firstName", "lastName", "employee")).toString();
    }
}
