package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class BuildingController {

    @Autowired
    private transient BuildingService buildingService;

    @RequestMapping("/sayHiToBuilding")
    public String sayHi() {
        return "Hello from the building!";
    }

    @RequestMapping("/buildings")
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @RequestMapping("/getBuilding/{id}")
    public Building getBuilding(@PathVariable("id") int id) {
        return buildingService.getBuilding(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/buildings")
    public void addBuilding(@RequestBody Building building) {
        buildingService.addBuilding(building);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/buildings/{id}")
    public void updateBuilding(@RequestBody Building building, @PathVariable("id") int id) {
        buildingService.updateBuilding(id, building);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/buildings/{id}")
    public void deleteBuilding(@PathVariable("id") int id) {
        buildingService.deleteBuilding(id);
    }
}

