package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class BuildingController {

    /** Returns all the buildings in the system.
     *
     * @return list of the buildings.
     */
    @RequestMapping("/getBuildings")
    public List getBuildings() {
        String uri = "http://localhost:8082/buildings";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }

    /** Returns a specific building with respect to its id.
     *
     * @param id the id of the building we want.
     * @return the building we are searching for.
     */
    @RequestMapping("/getBuilding/{id}")
    public Building getBuilding(@PathVariable("id") int id) {
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(id));
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Building.class);
    }

    /** Adds a building to the system.
     *
     * @param building the building we want to add.
     * @return true if its successfully added, else false.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/postBuilding")
    public boolean postBuilding(@RequestBody Building building) {
        try {
            String uri = "http://localhost:8082/buildings";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, building, void.class);
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
    @RequestMapping(method = RequestMethod.PUT, value = "/putBuilding/{id}")
    public boolean updateBuilding(@RequestBody Building building, @PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.put(uri, building);
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
    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBuilding/{id}")
    public boolean deleteBuilding(@PathVariable("id") int id) {
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
