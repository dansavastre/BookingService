package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

    /** Returns all the users in the system.
     *
     * @return list of users.
     */
    @RequestMapping("/getUsers")
    public List getUsers() {
        String uri = "http://localhost:8081/users";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }

    /** Returns a specific user with respect to its id.
     *
     * @param id the id of the user we want.
     * @return the user we are searching for.
     */
    @RequestMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") String id) {
        String uri = "http://localhost:8081/getUser/".concat(id);
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, User.class);
    }

    /** Adds a user to the system.
     *
     * @param user the user we want to add.
     * @return true if its successfully added, else false.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/postUser")
    public boolean postUser(@RequestBody User user) {
        try {
            String uri = "http://localhost:8081/users";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, user, void.class);
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
    @RequestMapping(method = RequestMethod.PUT, value = "/putUser/{id}")
    public boolean updateUser(@RequestBody User user, @PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/users/".concat(id);
            RestTemplate template = new RestTemplate();
            template.put(uri, user);
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

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteUser/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        try {
            String uri = "http://localhost:8081/users/".concat(id);
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
