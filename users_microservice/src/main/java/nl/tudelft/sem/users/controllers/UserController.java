package nl.tudelft.sem.users.controllers;

import java.util.List;
import nl.tudelft.sem.users.objects.User;
import nl.tudelft.sem.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private transient UserService userService;

    @GetMapping("/sayHi")
    @ResponseBody
    public String sayHi() {
        return "Hello from the user microservice!";
    }

    @GetMapping("admin/users")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("admin/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") String id) {
        return userService.getUser(id);
    }

    @PostMapping("admin/users")
    @ResponseBody
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("admin/users/{id}")
    @ResponseBody
    public void updateUser(@RequestBody User user, @PathVariable("id") String id) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("admin/users/{id}")
    public void deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
    }
}
