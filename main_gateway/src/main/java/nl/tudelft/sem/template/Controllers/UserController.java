package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @RequestMapping("/helloUser")
    public String helloUser(){
        String uri = "http://localhost:8081/sayHi";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(uri, String.class);
        return result;
    }

    @RequestMapping("/getUsers")
    public List<User> getUsers(){
        String uri = "http://localhost:8081/users";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }


    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") String id){
        String uri = "http://localhost:8081/getUser/".concat(id);
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, User.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postUser")
    public boolean postUser(@RequestBody User user){
        try {
            String uri = "http://localhost:8081/users";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, user, void.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putUser/{id}")
    public boolean updateUser(@RequestBody User user, @PathVariable("id") String id){
        try {
            String uri = "http://localhost:8081/users/".concat(id);
            RestTemplate template = new RestTemplate();
            template.put(uri, user);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
