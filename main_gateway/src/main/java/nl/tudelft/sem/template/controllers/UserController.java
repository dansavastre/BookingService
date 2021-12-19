package nl.tudelft.sem.template.controllers;

import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UserController {

    @Autowired
    private transient RestTemplate restTemplate;


    /** Sends the request to user for authentication of the user.
     *
     * @param password      encrypted user password
     * @param username      username of the user
     * @return Map of tokens received form the user microservice
     */
    @PostMapping("/login")
    @ResponseBody
    public Map loginUser(@RequestHeader("Authentication") String password,
                                         @RequestHeader("Username") String username) {
        String uri = "http://localhost:8081/login";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", password);
        headers.add("Username", username);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<Map> res = restTemplate
                    .exchange(uri, HttpMethod.POST, entity, Map.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "");
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns all the users in the system.
     *
     * @return list of users.
     */
    @GetMapping("/getUsers")
    @ResponseBody
    public List getUsers() {
        String uri = "http://localhost:8081/users";
        return restTemplate.getForObject(uri, List.class);
    }

    /** Returns a specific user with respect to its id.
     *
     * @param id the id of the user we want.
     * @return the user we are searching for.
     */
    @GetMapping("/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") String id) {
        String uri = "http://localhost:8081/getUser/".concat(id);
        return restTemplate.getForObject(uri, User.class);
    }

    /** Adds a user to the system.
     *
     * @param user the user we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postUser")
    @ResponseBody
    public boolean postUser(@RequestBody User user) {
        try {
            String uri = "http://localhost:8081/users";
            restTemplate.postForObject(uri, user, void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Update a user.
     *
     * @param user the new user.
     * @param id the id of the user to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putUser/{id}")
    @ResponseBody
    public boolean updateUser(@RequestBody User user, @PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/users/".concat(id);
            restTemplate.put(uri, user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Deletes a user from the system.
     *
     * @param id the id of the user to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    public boolean deleteUser(@PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/users/".concat(id);
            restTemplate.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
