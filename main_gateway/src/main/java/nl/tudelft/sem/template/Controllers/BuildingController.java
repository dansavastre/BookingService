package nl.tudelft.sem.template.Controllers;


import nl.tudelft.sem.template.Objects.Building;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BuildingController {

    @RequestMapping("/getBuildings")
    public List<Building> getBuildings(){
        String uri = "http://localhost:8082/buildings";
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, List.class);
    }


    @RequestMapping("/getBuilding/{id}")
    public Building getBuilding(@PathVariable("id") int id){
        String uri = "http://localhost:8082/getBuilding/".concat(String.valueOf(id));
        RestTemplate template = new RestTemplate();
        return template.getForObject(uri, Building.class);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/postBuilding")
    public boolean postBuilding(@RequestBody Building building){
        try {
            String uri = "http://localhost:8082/buildings";
            RestTemplate template = new RestTemplate();
            template.postForObject(uri, building, void.class);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/putBuilding/{id}")
    public boolean updateBuilding(@RequestBody Building building, @PathVariable("id") int id){
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.put(uri, building);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteBuilding/{id}")
    public boolean deleteBuilding(@PathVariable("id") int id){
        try {
            String uri = "http://localhost:8082/buildings/".concat(String.valueOf(id));
            RestTemplate template = new RestTemplate();
            template.delete(uri);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
