package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
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
public class BuildingController {

    private transient RestTemplate restTemplate = new RestTemplate();

    /** Returns all the buildings in the system.
     *
     * @param token     the token of the user
     * @return list of the buildings.
     */
    @GetMapping("/getBuildings")
    @ResponseBody
    public List getBuildings(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/buildings";

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

    /** Returns a specific building with respect to its id.
     *
     * @param id        the id of the building we want.
     * @param token     the token of the user
     * @return the building we are searching for.
     */
    @GetMapping("/getBuilding/{id}")
    @ResponseBody
    public Building getBuilding(@PathVariable("id") int id,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(id));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        try {
            ResponseEntity<Building> res = restTemplate
                    .exchange(uri, HttpMethod.GET, entity, Building.class);
            return res.getBody();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Adds a building to the system.
     *
     * @param token     the token of the user
     * @param building the building we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping("/postBuilding")
    @ResponseBody
    public boolean postBuilding(@RequestBody Building building,
                                @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/buildings";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Building> entity = new HttpEntity<>(building, headers);

        try {
            restTemplate.exchange(uri, HttpMethod.POST, entity, void.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Update a building.
     *
     * @param building the new building.
     * @param id the id of the building to update.
     * @param token     the token of the user
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBuilding/{id}")
    @ResponseBody
    public boolean updateBuilding(@RequestBody Building building,
                                  @PathVariable("id") int id,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<Building> entity = new HttpEntity<>(building, headers);

        try {
            ResponseEntity<Boolean> res = restTemplate
                    .exchange(uri, HttpMethod.PUT, entity, Boolean.class);
            return Boolean.TRUE.equals(res.getBody());
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }

    /** Deletes a building from the system.
     *
     * @param id the id of the building to delete.
     * @param token     the token of the user
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping("/deleteBuilding/{id}")
    @ResponseBody
    public boolean deleteBuilding(@PathVariable("id") int id,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<Boolean> res = restTemplate
                    .exchange(uri, HttpMethod.DELETE, entity, Boolean.class);
            return Boolean.TRUE.equals(res.getBody());
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "");
        }
    }
}
