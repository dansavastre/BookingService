package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class RoleController {

    @Autowired
    private transient RestTemplate restTemplate;

    @Bean
    public RestTemplate templateCreator() {
        return new RestTemplate();
    }

    /** Returns all the roles in the system.
     *
     * @return list of roles.
     */
    @GetMapping("/getRoles")
    @ResponseBody
    public List getRoles() {
        String uri = "http://localhost:8081/roles";
        return restTemplate.getForObject(uri, List.class);
    }

    /** Returns a specific role with respect to its id.
     *
     * @param id the id of the role we want.
     * @return the role we are searching for.
     */
    @GetMapping("/getRole/{id}")
    @ResponseBody
    public Role getRole(@PathVariable("id") Long id) {
        String uri = "http://localhost:8081/getRole/".concat(String.valueOf(id));
        return restTemplate.getForObject(uri, Role.class);
    }

    /** Adds a role to the system.
     *
     * @param role the role we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postRole")
    @ResponseBody
    public boolean postRole(@RequestBody Role role) {
        try {
            String uri = "http://localhost:8081/roles";
            restTemplate.postForObject(uri, role, void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Update a role.
     *
     * @param role the new role.
     * @param id the id of the role to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putRole/{id}")
    @ResponseBody
    public boolean updateRole(@RequestBody Role role, @PathVariable("id") Long id) {
        try {
            String uri = "http://localhost:8081/roles/".concat(String.valueOf(id));
            restTemplate.put(uri, role);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Deletes a role from the system.
     *
     * @param id the id of the role to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteRole/{id}")
    @ResponseBody
    public boolean deleteRole(@PathVariable("id") Long id) {
        try {
            String uri = "http://localhost:8081/roles/".concat(String.valueOf(id));
            restTemplate.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
