package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@Controller
public class BuildingController {

    private final transient RestTemplate restTemplate = new RestTemplate();

    /** Returns all the buildings in the system.
     *
     * @return list of the buildings.
     */
    @GetMapping("/getBuildings")
    @ResponseBody
    public List getBuildings() {
        String uri = "http://localhost:8082/buildings";
        return restTemplate.getForObject(uri, List.class);
    }

    /** Returns a specific building with respect to its id.
     *
     * @param id the id of the building we want.
     * @return the building we are searching for.
     */
    @GetMapping("/getBuilding/{id}")
    @ResponseBody
    public Building getBuilding(@PathVariable("id") int id) {
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(id));
        return restTemplate.getForObject(uri, Building.class);
    }

    /** Adds a building to the system.
     *
     * @param building the building we want to add.
     * @return true if its successfully added, else false.
     */
    @PostMapping( "/postBuilding")
    @ResponseBody
    public boolean postBuilding(@RequestBody Building building) {
        try {
            String uri = "http://localhost:8082/buildings";
            restTemplate.postForObject(uri, building, void.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Update a building.
     *
     * @param building the new building.
     * @param id the id of the building to update.
     * @return true if successfully updated, else false.
     */
    @PutMapping("/putBuilding/{id}")
    @ResponseBody
    public boolean updateBuilding(@RequestBody Building building, @PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            restTemplate.put(uri, building);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Deletes a building from the system.
     *
     * @param id the id of the building to delete.
     * @return true if successfully deleted, else false.
     */
    @DeleteMapping( "/deleteBuilding/{id}")
    @ResponseBody
    public boolean deleteBuilding(@PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            restTemplate.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
