package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.ArrayList;

@RestController
public class UserController {

    @GetMapping("/addUser")
    public User addUser(@RequestParam(value = "id") String id,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "fName") String fName,
                        @RequestParam(value = "lName") String lName,
                        @RequestParam(value = "userType") String userType){
        return new User(id, password, fName, lName, userType);
    }


    @GetMapping("/getUser")
    public String getUser(){
        String uri = "http://localhost:8081/getUsers";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }

}
