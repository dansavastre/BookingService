package nl.tudelft.sem.main.controllers;

import nl.tudelft.sem.main.objects.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class SecondBuildingController {

    @Autowired
    private transient RestTemplate restTemplate;


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

    /**
     * Helper method for sending get requests for buildings.
     *
     * @param token the user's authorization token
     * @param uri the address to send request to
     * @return the building if it exists
     * @throws HttpClientErrorException otherwise
     */
    protected Building sendGetBuildingRequest(@RequestHeader(HttpHeaders.AUTHORIZATION)
                                                      String token,
                                              String uri) throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Building> res = restTemplate
                .exchange(uri, HttpMethod.GET, entity, Building.class);
        return res.getBody();
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



}
