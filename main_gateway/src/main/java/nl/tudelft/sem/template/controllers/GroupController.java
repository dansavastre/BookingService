package nl.tudelft.sem.template.controllers;

import nl.tudelft.sem.template.objects.Group;
import nl.tudelft.sem.template.objects.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private transient RestTemplate restTemplate;

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

    @GetMapping("/getGroup/{id}")
    @ResponseBody
    public Group getGroup(@PathVariable("id") Long id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/admin/getGroup/".concat(id.toString());
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

    @GetMapping("/getMyGroup/{id}")
    @ResponseBody
    public Group getMyGroup(@PathVariable("id") Long id,
                        @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8081/secretary/getGroup/".concat(id.toString());
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
