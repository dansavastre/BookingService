package nl.tudelft.sem.template.Controllers;

import nl.tudelft.sem.template.Objects.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
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

//    @RequestMapping("/getUser")
//    public String getUser(){
//        String uri = "http://localhost:8081/getUser";
//        RestTemplate template = new RestTemplate();
//        User[] result = ((template.getForObject(uri, User[].class)));
//        assert result != null;
//        return Arrays.toString(result);
//    }
}
