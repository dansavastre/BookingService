package nl.tudelft.sem.main.controllers;

import java.util.List;
import nl.tudelft.sem.main.objects.Group;
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
public class GroupController {

    @Autowired
    private transient RestTemplate restTemplate;

    /**
     * Function to get all groups.
     *
     * @param token Security token for authentication
     * @return A list of groups
     */
    @GetMapping("/getGroups")
    @ResponseBody
    public List getGroups(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/groups";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<List> res = restTemplate
                    .exchange(uri, HttpMethod.GET, entity, List.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Get a specific group by their ID.
     *
     * @param id    ID of the group
     * @param token The security token for authentication
     * @return The group, if it exists
     */
    @GetMapping("/getGroup/{id}")
    @ResponseBody
    public Group getGroup(@PathVariable("id") Long id,
                          @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/getGroup/".concat(id.toString());
        return getGroupRequest(token, uri);
    }

    /**
     * Sends a GET request to get a Group object.
     *
     * @param token     user's authorization token
     * @param uri       address for the request
     * @return          received Group object
     */
    Group getGroupRequest(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, String uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("", headers);
        try {
            ResponseEntity<Group> res = restTemplate.exchange(uri,
                    HttpMethod.GET, entity, Group.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /**
     * Getter for a specific group to be used by secretaries to find their group.
     *
     * @param id    ID of the secretary
     * @param token Security token for authentication
     * @return The group of the secretary
     */
    @GetMapping("/getMyGroup/{id}/{secretaryId}")
    @ResponseBody
    public Group getMyGroup(@PathVariable("id") Long id,
                            @PathVariable("secretaryId") String secretaryId,
                            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/secretary/getMyGroup/"
                    + id.toString() + "/"
                    + secretaryId;
        return getGroupRequest(token, uri);
    }

    /**
     * Post a new group to the database.
     *
     * @param group The group to be posted
     * @param token Security token for authentication
     * @return boolean indicating success or failure
     */
    @PostMapping("/postGroup")
    @ResponseBody
    public boolean postGroup(@RequestBody Group group,
                             @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/group";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Group> entity = new HttpEntity<>(group, headers);
        try {
            restTemplate.exchange(uri,
                    HttpMethod.POST, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Update a group in the database.
     *
     * @param group The group to be updated, with updated elements
     * @param id    The ID of the group to be updated
     * @param token Security token for authentication
     * @return Boolean indicating success or failure
     */
    @PutMapping("/putGroup/{id}")
    @ResponseBody
    public boolean updateGroup(@RequestBody Group group, @PathVariable("id") String id,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/groups/".concat(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Group> entity = new HttpEntity<>(group, headers);
        try {
            restTemplate.exchange(uri,
                    HttpMethod.PUT, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method to delete an existing group.
     *
     * @param id    ID of the group to be deleted
     * @param token Security token for authentication
     * @return Boolean indicating success or failure
     */
    @DeleteMapping("/deleteGroup/{id}")
    @ResponseBody
    public boolean deleteGroup(@PathVariable("id") String id,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/groups/".concat(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(uri,
                    HttpMethod.DELETE, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            return false;
        }
    }

}
