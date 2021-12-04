package nl.tudelft.sem.template.controllers;

import java.util.List;
import nl.tudelft.sem.template.objects.Building;
import nl.tudelft.sem.template.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class BuildingController {

    @Autowired
    private transient BuildingService buildingService;

    @GetMapping("/sayHiToBuilding")
    @ResponseBody
    public String sayHi() {
        return "Hello from the building!";
    }

    @GetMapping("/buildings")
    @ResponseBody
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/getBuilding/{id}")
    @ResponseBody
    public Building getBuilding(@PathVariable("id") int id) {
        return buildingService.getBuilding(id);
    }

    @PostMapping("/buildings")
    @ResponseBody
    public void addBuilding(@RequestBody Building building) {
        buildingService.addBuilding(building);
    }

    @PutMapping("/buildings/{id}")
    @ResponseBody
    public void updateBuilding(@RequestBody Building building, @PathVariable("id") int id) {
        buildingService.updateBuilding(id, building);
    }

    @DeleteMapping("/buildings/{id}")
    @ResponseBody
    public void deleteBuilding(@PathVariable("id") int id) {
        buildingService.deleteBuilding(id);
    }
}

