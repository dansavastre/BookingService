package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    @RequestMapping("/helloUser")
    public String helloUser(){
        String uri = "http://localhost:8081/sayHi";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }

    @GetMapping("/addUser")
    public User addUser(User user){
        return user;
    }


}
